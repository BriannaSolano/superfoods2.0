package com.example.myapplication;

public class Diet {
    private String None, Vegetarian;
    public Diet(){
    }
    public Diet(String none, String vegetarian){
        None = none;
        Vegetarian = vegetarian;
    }
    public String getNone() {
        return None;
    }

    public void setNone(String none) {
        None = none;
    }

    public String getVegetarian() {
        return Vegetarian;
    }

    public void setVegetarian(String vegetarian) {
        Vegetarian = vegetarian;
    }
}
