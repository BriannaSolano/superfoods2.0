package com.example.myapplication;

public class CartInfo {
    private String FoodCode, Quantity, EliminateIngredients;
    public CartInfo(String foodCode){
        FoodCode = foodCode;
    }


    public CartInfo(String foodCode, String quantity, String eliminateIngredients) {
        FoodCode = foodCode;
        Quantity = quantity;
        EliminateIngredients = eliminateIngredients;
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

    public void setEliminateIngredients(String eliminateIngredients) {
        EliminateIngredients = eliminateIngredients;
    }
}
