package com.example.resturantmanage;


//Object class Food created to retreive data from DB
public class Food {
    private String Description, Name, Price, MenuID;

    public Food() {
    }

    public Food(String description, String name, String price, String menuID) {
        Description = description;
        Name = name;
        Price = price;
        MenuID = menuID;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getMenuID() {
        return MenuID;
    }

    public void setMenuID(String menuID) {
        MenuID = menuID;
    }
}
