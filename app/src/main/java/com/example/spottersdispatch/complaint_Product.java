package com.example.spottersdispatch;

public class complaint_Product {
    private String Title;
    private String Date;
    private String Sub_text;

    public complaint_Product(String title, String date, String sub_text) {
        Title = title;
        Date = date;
        Sub_text = sub_text;
    }

    public String getTitle() {
        return Title;
    }

    public String getDate() {
        return Date;
    }

    public String getSub_text() {
        return Sub_text;
    }
}
