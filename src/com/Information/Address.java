package com.Information;

public class Address {

    // Creates a valid address;
    private String street;
    private String town;
    private String county;
    private String eircode;

    public Address() {
    }
    
    public Address(String street, String town, String county, String eircode) {

        setStreet(street);
        setTown(town);
        setCounty(county);
        setEircode(eircode);
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreet() {
        return street;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getTown() {
        return town;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getCounty() {
        return county;
    }

    public void setEircode(String eircode) {
        this.eircode = eircode;
    }

    public String getEircode() {
        return eircode;
    }

    public String toString() {
        return "\nStreet: " + street + "\nTown: " + town + "\nCounty: " + county + "\nEircode: " + eircode + "\n\n";
    }
}
