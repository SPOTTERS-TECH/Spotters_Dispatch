package com.example.spottersdispatch;

public class Product {
    private String Order_id;
    private String Receipient_name;
    private String Receipient_Phone;
    private String Reciepient_Address;
    private String package_name;
    private String package_weight;
    private String receiver_name;
    private String receiver_phone;
    private String destination;
    private String statusoforder;


    public Product() {

    }

    public Product(String order_id, String receipient_name, String receipient_Phone, String reciepient_Address, String package_name, String package_weight, String receiver_name, String receiver_phone, String destination, String statusoforder) {
        Order_id = order_id;
        Receipient_name = receipient_name;
        Receipient_Phone = receipient_Phone;
        Reciepient_Address = reciepient_Address;
        this.package_name = package_name;
        this.package_weight = package_weight;
        this.receiver_name = receiver_name;
        this.receiver_phone = receiver_phone;
        this.destination = destination;
        this.statusoforder = statusoforder;
    }

    public String getOrder_id() {
        return Order_id;
    }

    public String getReceipient_name() {
        return Receipient_name;
    }

    public String getReceipient_Phone() {
        return Receipient_Phone;
    }

    public String getReciepient_Address() {
        return Reciepient_Address;
    }

    public String getPackage_name() {
        return package_name;
    }

    public String getPackage_weight() {
        return package_weight;
    }

    public String getReceiver_name() {
        return receiver_name;
    }

    public String getReceiver_phone() {
        return receiver_phone;
    }

    public String getDestination() {
        return destination;
    }

    public String getStatusoforder() {
        return statusoforder;
    }
}
