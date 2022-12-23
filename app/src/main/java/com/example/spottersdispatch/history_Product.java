package com.example.spottersdispatch;

public class history_Product {
    private String Rec_name;
    private String destination;
    private String Order_id;
    private String Phone;
    private String rec_address;
    private String distance;

    public history_Product(String rec_name, String destination, String order_id, String phone, String rec_address, String distance) {
        Rec_name = rec_name;
        this.destination = destination;
        Order_id = order_id;
        Phone = phone;
        this.rec_address = rec_address;
        this.distance = distance;
    }

    public String getRec_name() {
        return Rec_name;
    }

    public String getDestination() {
        return destination;
    }

    public String getOrder_id() {
        return Order_id;
    }

    public String getPhone() {
        return Phone;
    }

    public String getRec_address() {
        return rec_address;
    }

    public String getDistance() {
        return distance;
    }
}
