package com.example.afiqur.tourmatepro.PhotoMoment;

import android.content.CursorLoader;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.SimpleMultiPartRequest;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.afiqur.tourmatepro.Data;
import com.example.afiqur.tourmatepro.LoginActivity;
import com.example.afiqur.tourmatepro.Moment.ActivityMoment;
import com.example.afiqur.tourmatepro.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.example.afiqur.tourmatepro.Data.ImageInsert;

public class AddPhotoMoment extends AppCompatActivity {
    private ImageView imageView;
    private Button btnChoose, btnUpload,cancelbtn;
    private ProgressBar progressBar;
    String eventId;
    EditText photoCaption;

    public static String BASE_URL = ImageInsert;
    static final int PICK_IMAGE_REQUEST = 1;
    String filePath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_moment);

        imageView = (ImageView) findViewById(R.id.imageView);
        btnChoose = (Button) findViewById(R.id.button_choose);
        btnUpload = (Button) findViewById(R.id.button_upload);
        photoCaption = (EditText) findViewById(R.id.photoCaption);
        cancelbtn = (Button) findViewById(R.id.cancel);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                eventId= null;
            } else {
                eventId= extras.getString("EventID");
            }
        } else {
            eventId= (String) savedInstanceState.getSerializable("EventID");
        }

        cancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(AddPhotoMoment.this, ActivityMoment.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intent);
                finish();
            }
        });

        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                imageBrowse();
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (filePath != null && !photoCaption.getText().toString().equals("")) {

                    imageUpload(filePath);
                } else {
                    Toast.makeText(getApplicationContext(), "Image not selected!", Toast.LENGTH_LONG).show();
                }

            }
        });
    }


    private void imageBrowse() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Start the Intent
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            if(requestCode == PICK_IMAGE_REQUEST){
                Uri picUri = data.getData();

                filePath = getPath(picUri);

                Log.d("picUri", picUri.toString());
                Log.d("filePath", filePath);

                imageView.setImageURI(picUri);

            }

        }

    }

    private void imageUpload(final String imagePath) {


        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        final String userid = preferences.getString("userid", "0");

        SimpleMultiPartRequest smr = new SimpleMultiPartRequest(Request.Method.POST, BASE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response", response);
                        try {
                            JSONObject jObj = new JSONObject(response);
                            String message = jObj.getString("message");

                            if(message.equals("success")){

                               // addPhotoMoment();
                                Toast.makeText(AddPhotoMoment.this, "success", Toast.LENGTH_SHORT).show();
//                                Intent intent = new Intent(AddPhotoMoment.this, ActivityMoment.class);
//                                startActivity(intent);
                                finish();
                            }

                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();

                        } catch (JSONException e) {
                            // JSON error
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }


        );

        smr.addFile("image", imagePath);
        smr.addStringParam("userid", userid.toString().trim());
        smr.addStringParam("EventID", eventId.toString().trim());
        smr.addStringParam("photoCaption", photoCaption.getText().toString().trim());

        RequestQueue requestQueue2 = Volley.newRequestQueue(this);
        requestQueue2.add(smr);

       // MyApplication.getInstance().addToRequestQueue(smr);

    }

    public void addPhotoMoment(final String imageName){


        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        final String userid = preferences.getString("userid", "0");



        StringRequest stringRequest = new StringRequest(Request.Method.POST, Data.addImageData,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        if(response.trim().equals("success")){
                            Toast.makeText(AddPhotoMoment.this, "Added Successfully", Toast.LENGTH_SHORT).show();
                            finish();
                        }else{
                            Toast.makeText(AddPhotoMoment.this, "Try Again", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(AddPhotoMoment.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();

                params.put("userid", userid.toString().trim());
                params.put("EventID", eventId.toString().trim());
                params.put("imageName", imageName.toString().trim());


                return params;
            }

        };

        RequestQueue requestQueue2 = Volley.newRequestQueue(this);
        requestQueue2.add(stringRequest);

    }

    private String getPath(Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        CursorLoader loader = new CursorLoader(getApplicationContext(), contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }

}
