package com.example.duyanhtang.projectonlinepharmacy;

/**
 * Created by Duy Anh Tang on 4/28/2017.
 */

public class Transaction {
    String prod_name,date,category;
    int prod_quantity,id;
    double price;

   public Transaction(String prName,int quant,String date, int id, double price,String category){
        this.prod_name=prName;
        this.prod_quantity=quant;
        this.date=date;
        this.price=price;
        this.id=id;
       this.category=category;
    }

    @Override
    public String toString() {
        return "Name: "+ prod_name+
                "\n date: "+date+
                "\n Quantity: "+prod_quantity+
                "\n Price: "+price+
                "\n id: "+id
                + "\n Category: "+category;
    }
}
