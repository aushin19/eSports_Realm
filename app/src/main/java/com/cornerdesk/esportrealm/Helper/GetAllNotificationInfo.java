package com.cornerdesk.esportrealm.Helper;

public class GetAllNotificationInfo {

    String Title, Body, Date, Link;

    public GetAllNotificationInfo(){};

    public GetAllNotificationInfo(String title, String body, String date, String link) {
        Title = title;
        Body = body;
        Date = date;
        Link = link;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getBody() {
        return Body;
    }

    public void setBody(String body) {
        Body = body;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getLink() {
        return Link;
    }

    public void setLink(String link) {
        Link = link;
    }
}
