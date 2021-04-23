package com.example.myapplication.inventory;

public class Items {
    private String name;
    private String category;
    private String price;
    private int count;
    private int min;

public Items() {

}

public Items(String name, String category, String price, int count, int min) {

    this.name=name;
    this.category=category;
    this.price=price;
    this.count=count;
    this.min=min;
}

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getPrice() {
        return price;
    }

    public int getCount() {return count;}

    public int getMin() {return min;}
}