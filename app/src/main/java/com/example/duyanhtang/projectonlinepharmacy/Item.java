package com.example.duyanhtang.projectonlinepharmacy;

/**
 * Created by dani1covi on 4/25/17.
 */

public class Item {
     String name;
     String description;
     int Stock;
     double price;
    String category;

    public Item(){
        this.price=0.0;
        this.description="";
        this.description="";
        this.Stock=0;
    }

    public Item(String n, String des, int St,String category, double price){
        this.name=n;
        this.description=des;
        this.Stock=St;
        this.price=price;
        this.category=category;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return Stock;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setStock(int stock) {
        Stock = stock;
    }

    @Override
    public String toString() {
        return "Name: "+ name+
                "\n Description: "+description+
                "\n Quantity: "+Stock+
                "\n Price: "+price+
                "\n Category: "+category;

    }
}
