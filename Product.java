package Inventory;

import java.util.Scanner;

public class Product {

    String name;
    double price;
    int stock;
    int itemNumber;
    boolean active= true;

    public Product(){
        this.name = "";
        this.price = 0.0;
        this.stock = 0;
        this.itemNumber = 0;
        this.active = false;
    }
    public Product(String name, double price, int stock, int itemNumber, boolean active ){
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.itemNumber = itemNumber;
        this.active = active;
    }

    public String getName(){
        return name;
    }
    public double getPrice(){
        return price;
    }
    public int getStock(){
        return stock;
    }
    public boolean getActive(){
        return active;
    }
    public int getItemNumber(){
        return itemNumber;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setPrice(double price){
        this.price = price;
    }
    public void setStock(int stock){
        this.stock = stock;
    }
    public void setItemNumber(int itemNumber){
        this.itemNumber = itemNumber;
    }
    public void setActive(boolean active){
        this.active = active;
    }
    public double getInventoryValue(int stock, double price){
        return stock*price;
    }
    @Override
    public String toString(){
        return ("Name             : "+name
                +"\nPrice            : "+price
                +"\nQuantity in stock: "+stock
                +"\nProduct number   : "+itemNumber
                +"\nStock value      : "+getInventoryValue(stock,price)
                +"\nProduct status   : "+active );


    }
    public int addToInventory(int itemsToAddOrDeduct){
        return stock+itemsToAddOrDeduct;
    }
    public int deductFromInventory(int itemsToAddOrDeduct){
        return stock-itemsToAddOrDeduct;
    }

}
