package com.example.login;

import android.net.Uri;
import android.widget.Spinner;

import java.sql.Date;

public class Event {

    public String description,location,category,date,fullName,age,imageUrl;
    Event(String desc,String loc,String dat){
        description=desc;
        location=loc;
        date=dat;
    }
    Event(String desc,String loc,String dat,String imageUrl){
        description=desc;
        location=loc;
        date=dat;
        this.imageUrl=imageUrl;
    }
    Event(String desc,String loc,String dat,String imageUrl,String ime,String age){
        description=desc;
        location=loc;
        date=dat;
        this.imageUrl=imageUrl;
        fullName=ime;
        this.age=age;
    }
    Event(String desc,String loc, String cat,String dat,String imageUrl){
        description=desc;
        location=loc;
        category=cat;
        date=dat;
        this.imageUrl=imageUrl;
    }
    Event(String desc,String loc, String cat,String dat,String imageUrl,String ime,String age){
        description=desc;
        location=loc;
        category=cat;
        date=dat;
        fullName=ime;
        this.imageUrl=imageUrl;
        this.age=age;
    }

}
