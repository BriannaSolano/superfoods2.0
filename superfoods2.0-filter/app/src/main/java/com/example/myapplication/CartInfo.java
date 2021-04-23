package com.example.myapplication;

import java.util.List;

public class CartInfo {
    private String FoodCode, Quantity, EliminateIngredients,FoodName,TotalFoodPrice;

    public CartInfo(String foodCode, String quantity, String eliminateIngredients, String foodName, String totalFoodPrice) {
        FoodCode = foodCode;
        Quantity = quantity;
        EliminateIngredients = eliminateIngredients;
        FoodName = foodName;
        TotalFoodPrice = totalFoodPrice;
    }

    public CartInfo() {
    }

    public String getFoodCode() {
        return FoodCode;
    }

    public void setFoodCode(String foodCode) {
        FoodCode = foodCode;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getEliminateIngredients() {
        return EliminateIngredients;
    }


    public String getFoodName() {
        return FoodName;
    }

    public void setFoodName(String foodName) {
        FoodName = foodName;
    }

    public String getTotalFoodPrice() {
        return TotalFoodPrice;
    }

    public void setTotalFoodPrice(String totalFoodPrice) {
        TotalFoodPrice = totalFoodPrice;
    }
}
