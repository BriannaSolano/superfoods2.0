package com.example.myapplication;

public class SetOrder {

    private String IDofFood;
    private String NameofFood;
    private String Amount;
    private String Price;
    private String Discount;

    public SetOrder(String IDofFood, String nameofFood, String amount, String price, String discount) {
        this.IDofFood = IDofFood;
        NameofFood = nameofFood;
        Amount = amount;
        Price = price;
        Discount = discount;
    }

    public String getIDofFood() {
        return IDofFood;
    }

    public void setIDofFood(String IDofFood) {
        this.IDofFood = IDofFood;
    }

    public String getNameofFood() {
        return NameofFood;
    }

    public void setNameofFood(String nameofFood) {
        NameofFood = nameofFood;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getDiscount() {
        return Discount;
    }

    public void setDiscount(String discount) {
        Discount = discount;
    }
}
