package com.example.maartenvandenhof.studentmenu;

import java.util.ArrayList;

public class Menu {
    private String name;
    private ArrayList<Ingredient> ingredient;
    private double price;

    public Menu(String name, ArrayList<Ingredient> ingredient, double price) {
        this.name = name;
        this.ingredient = ingredient;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Ingredient> getIngredient() {
        return ingredient;
    }

    public void setIngredient(ArrayList<Ingredient> ingredient) {
        this.ingredient = ingredient;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
