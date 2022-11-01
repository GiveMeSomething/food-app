package com.example.foodapp.firebase.entity;

import com.example.foodapp.repository.enums.Cuisine;
import com.example.foodapp.repository.enums.Flavor;
import com.example.foodapp.repository.enums.Intolerance;
import com.example.foodapp.repository.enums.MealType;

import java.util.ArrayList;

public class UserPreference {
    private ArrayList<Cuisine> cuisineList;
    private ArrayList<Flavor> flavorList;
    private ArrayList<Intolerance> intoleranceList;
    private ArrayList<MealType> mealTypeList;

    public UserPreference() {
        this.cuisineList = new ArrayList<>();
        this.flavorList = new ArrayList<>();
        this.intoleranceList = new ArrayList<>();
        this.mealTypeList = new ArrayList<>();
    }

    public UserPreference(ArrayList<Cuisine> cuisineList, ArrayList<Flavor> flavorList,
                          ArrayList<Intolerance> intoleranceList, ArrayList<MealType> mealTypeList) {
        this.cuisineList = cuisineList;
        this.flavorList = flavorList;
        this.intoleranceList = intoleranceList;
        this.mealTypeList = mealTypeList;
    }
}
