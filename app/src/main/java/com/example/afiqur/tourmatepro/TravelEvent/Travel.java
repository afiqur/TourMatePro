package com.example.afiqur.tourmatepro.TravelEvent;

/**
 * Created by Istiyak on 07/05/17.
 */
public class Travel {

    String eventid;
    String eventname;
    String startdate;
    String enddate;
    String budget;
    String econtact;

    public Travel(String eventid, String eventname, String startdate, String enddate, String budget, String econtact) {
        this.eventid = eventid;
        this.eventname = eventname;
        this.startdate = startdate;
        this.enddate = enddate;
        this.budget = budget;
        this.econtact = econtact;
    }

    public String getEventid() {
        return eventid;
    }

    public String getEventname() {
        return eventname;
    }

    public String getStartdate() {
        return startdate;
    }

    public String getEnddate() {
        return enddate;
    }

    public String getBudget() {
        return budget;
    }

    public String getEcontact() {
        return econtact;
    }
}
