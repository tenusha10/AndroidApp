package com.example.resturantmanage;

import javax.sql.StatementEvent;

//Order object contains all information in an order and is stored in the local db
public class Order {
    private String ProductId;
    private String ProductName;
    private String Quantity;
    private String Price;

    public Order() {
    }

    public Order(String productId, String productName, String quantity, String price) {
        ProductId = productId;
        ProductName = productName;
        Quantity = quantity;
        Price = price;
    }

    public String getProductId() {
        return ProductId;
    }

    public void setProductId(String productId) {
        ProductId = productId;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }
}
