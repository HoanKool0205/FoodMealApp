package com.androidexp.englishapp.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MealsByCategoryList {

    @SerializedName("meals")
    @Expose
    private List<MealsByCategory> meals;

    public List<MealsByCategory> getMeals() {
        return meals;
    }

    public void setMeals(List<MealsByCategory> meals) {
        this.meals = meals;
    }
}
