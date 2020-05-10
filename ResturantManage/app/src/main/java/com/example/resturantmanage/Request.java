package com.example.resturantmanage;

import java.util.List;

//object requests is an order that is placed and saved in the firebase db conatisn the table no , total, and the List of food items that the order contains
public class Request {
    private String TableNo;
    private String Total;
    private List<Order> foods;

    public Request() {
    }

    public Request(String tableNo, String total, List<Order> foods) {
        TableNo = tableNo;
        Total = total;
        this.foods = foods;
    }

    public String getTableNo() {
        return TableNo;
    }

    public void setTableNo(String tableNo) {
        TableNo = tableNo;
    }

    public String getTotal() {
        return Total;
    }

    public void setTotal(String total) {
        Total = total;
    }

    public List<Order> getFoods() {
        return foods;
    }

    public void setFoods(List<Order> foods) {
        this.foods = foods;
    }
}
