package com.victortello.ws.webservice.model.request;

public class AddressRequestModel {

    private String city;
    private String country;
    private String streetName;
    private String type;

    public String getCity() {
        return city;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setCity(String city) {
        this.city = city;
    }

}
