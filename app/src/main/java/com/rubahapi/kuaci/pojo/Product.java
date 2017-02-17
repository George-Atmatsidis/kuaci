package com.rubahapi.kuaci.pojo;

/**
 * Created by prasetia on 2/13/2017.
 */

public class Product {
    private String name;
    private String barcode;
    private String imagepath;

    public Product() {
    }

    public Product(String name, String barcode) {
        this.name = name;
        this.barcode = barcode;
    }

    public Product(String name, String barcode, String imagepath) {
        this.name = name;
        this.barcode = barcode;
        this.imagepath = imagepath;
    }

    public String getImagepath() {
        return imagepath;
    }

    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }
}
