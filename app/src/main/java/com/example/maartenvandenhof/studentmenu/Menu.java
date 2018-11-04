package com.example.maartenvandenhof.studentmenu;

import java.util.ArrayList;

public class Menu {
    private String name;
    private ArrayList<Ingredient> ingredients;
    private String description;
    private double price = 0;



    public Menu(String name, ArrayList<Ingredient> ingredients, double price) {

        this.name = name;
        this.ingredients = ingredients;
        this.price = price;
    }

    public Menu(String name, ArrayList<Ingredient> ingredients) {
        this.name = name;
        this.ingredients = ingredients;
        this.price = getPrice();
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Ingredient> getIngredient() {
        return ingredients;
    }

    public void setIngredient(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice(){
        if (this.price == 0){
            for(Ingredient i : ingredients){
                this.price = price + i.getPrice();
            }
        }
        return this.price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}