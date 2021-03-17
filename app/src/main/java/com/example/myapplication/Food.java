package com.example.myapplication;

public class Food {
    private String Description, Discount, Image, MenuId, Name, Price;
    private int Calories;
    public Food() {
    }

    public Food(String description, String discount, String image, String menuId, String name, String price, int calories) {
        Description = description;
        Discount = discount;
        Image = image;
        MenuId = menuId;
        Name = name;
        Price = price;
        Calories = calories;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getDiscount() {
        return Discount;
    }

    public void setDiscount(String discount) {
        Discount = discount;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getMenuId() {
        return MenuId;
    }

    public void setMenuId(String menuId) {
        MenuId = menuId;
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

    public int getCalories() {
        return Calories;
    }

    public void setCalories(int calories) {
        Calories = calories;
    }
}
