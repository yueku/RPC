package com.yue.bean;


import java.io.Serializable;

public class Product implements Serializable {
    private int id;
    private String prdName;

    public Product(int id, String prdName) {
        this.id = id;
        this.prdName = prdName;
    }
    public Product() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPrdName() {
        return prdName;
    }

    public void setPrdName(String prdName) {
        this.prdName = prdName;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", prdName='" + prdName + '\'' +
                '}';
    }
}
