package com.rubahapi.kuaci.pojo;

/**
 * Created by prasetia on 2/13/2017.
 */

public class Product {
    private String name;
    private String barcode;

    public Product() {
    }

    public Product(String name, String barcode) {
        this.name = name;
        this.barcode = barcode;
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
