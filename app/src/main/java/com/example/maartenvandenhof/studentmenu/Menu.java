package com.example.maartenvandenhof.studentmenu;

import android.media.ImageReader;
import android.widget.ImageView;

import java.util.ArrayList;

public class Menu {
    private String name;
    private ArrayList<Ingredient> ingredients;
    private String description;
    private String recipe;
    private double price = 0;
    private int rating;
    private ImageView imageToUpload;

    public Menu(String name, ArrayList<Ingredient> ingredients, double price, String recipe, ImageView imageToUpload) {

        this.name = name;
        this.ingredients = ingredients;
        this.price = price;
        this.imageToUpload = imageToUpload;
        setRecipe(recipe);
    }

    public Menu(String name, ArrayList<Ingredient> ingredients, double price) {

        this.name = name;
        this.ingredients = ingredients;
        this.price = price;
        setRecipe("N/A");
    }

    public Menu(String name, ArrayList<Ingredient> ingredients, String description) {

        this.name = name;
        this.ingredients = ingredients;
        this.description = description;
        setRecipe("N/A");
    }

    public Menu(String name, ArrayList<Ingredient> ingredients) {
        this.name = name;
        this.ingredients = ingredients;
        this.price = getPrice();
        setRecipe("N/A \n \n \n Test Test Test");
    }

    public Menu(){
        setRecipe("N/A \n \n \n Test Test Test");
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

    public ArrayList<String> getIngredientsString(){
        ArrayList<String> list = new ArrayList<>();
        for(Ingredient i:ingredients){
            list.add(i.getName());
        }
        return list;
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

    public String getRecipe() {
        return recipe;
    }

    public void setRecipe(String recipe) {
        this.recipe = recipe;
    }
}