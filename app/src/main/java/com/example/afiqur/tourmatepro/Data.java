package com.example.afiqur.tourmatepro;

/**
 * Created by ISTIYAK on 5/13/2017.
 */

public class Data {



    public String getSignupUrl() {
        return signupUrl;
    }

    public String getLoginUrl() {
        return LoginUrl;
    }

    public static String ip = "http://192.168.0.109:8080/turemate/";
    public static String ip2 = "http://192.168.0.109:8080/";
    public static String signupUrl = ip+"user/doSignup";
    public static String LoginUrl = ip+"user/login";
    public static String TravelList = ip+"user/travel_list";
    public static String AddTravelEvent = ip+"user/add_travel_event";
    public static String UpdateTravelEvent = ip+"user/update_travel_event";
    public static String addExpenseCost = ip+"user/add_expense";
    public static String addImageData = ip+"user/add_image_data";
    public static String MomentDetails = ip+"user/load_moment_details";
    public static String MomentListUrl = ip+"user/load_all_moment";
    public static String MomentListDelete = ip+"user/delete_moment";
    public static String ExpenseListUrl = ip+"user/load_all_expense";
    public static String ExpenseDeleteUrl = ip+"user/deleteExpenseData";
    public static String ImageInsert = ip+"user/uploadtoserverImage";


}
