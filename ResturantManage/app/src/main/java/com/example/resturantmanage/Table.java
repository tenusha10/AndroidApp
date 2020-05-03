package com.example.resturantmanage;

public class Table {
    private String availability;
    private String tableNo;

    public Table() {
    }

    public Table(String availability, String tableNo) {
        this.availability = availability;
        this.tableNo = tableNo;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public String getTableNo() {
        return tableNo;
    }

    public void setTableNo(String tableNo) {
        this.tableNo = tableNo;
    }
}
