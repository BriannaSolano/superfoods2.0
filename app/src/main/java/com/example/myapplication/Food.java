package com.example.myapplication;

public class Food {
<<<<<<< Updated upstream
    private String Description, Discount, Image, MenuId, Name, Price, Calories;
=======
    private String Description, Discount, Image, MenuId, Name, Price, Calories, ItemID;
>>>>>>> Stashed changes
    private Diet Diet;
    private Allergy Allergy;
    public Food() {
    }

<<<<<<< Updated upstream
    public Food(String description, String discount, String image, String menuId, String name, String price, String calories, Diet diet, Allergy allergy) {
=======
    public Food(String description, String discount, String image, String menuId, String name, String price, String calories, String itemID, Diet diet, Allergy allergy) {
>>>>>>> Stashed changes
        Description = description;
        Discount = discount;
        Image = image;
        MenuId = menuId;
        Name = name;
        Price = price;
        Calories = calories;
<<<<<<< Updated upstream
=======
        ItemID = itemID;
>>>>>>> Stashed changes
        Diet = diet;
        Allergy = allergy;
    }

    public String getCalories() {
        return Calories;
    }

    public void setCalories(String calories) {
        Calories = calories;
    }

    public Diet getDiet() {return Diet;}

    public void setDiet(Diet diet) {Diet = diet;}

    public Allergy getAllergy() {return Allergy;}

    public void setAllergy(Allergy allergy) {Allergy = allergy;}

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

    public String getItemID() {
        return ItemID;
    }

    public void setItemID(String itemID) {
        ItemID = itemID;
    }
}
