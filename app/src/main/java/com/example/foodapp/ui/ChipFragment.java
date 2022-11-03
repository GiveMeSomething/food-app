package com.example.foodapp.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.foodapp.R;
import com.example.foodapp.repository.api.enums.Cuisine;
import com.example.foodapp.repository.api.enums.Flavor;
import com.example.foodapp.repository.api.enums.FoodTag;
import com.example.foodapp.repository.api.enums.Intolerance;
import com.example.foodapp.repository.api.enums.MealType;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;


public class ChipFragment extends Fragment {

    private ArrayList<Cuisine> selectedCuisines;
    private ArrayList<Flavor> selectedFlavors;
    private ArrayList<Intolerance> selectedIntolerances;
    private ArrayList<MealType> selectedMealTypes;

    private ChipGroup cuisineGroup;
    private ChipGroup flavorGroup;
    private ChipGroup intoleranceGroup;
    private ChipGroup mealTypeGroup;

    private Button submitBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        selectedCuisines = new ArrayList<>();
        selectedFlavors = new ArrayList<>();
        selectedIntolerances = new ArrayList<>();
        selectedMealTypes = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chip, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bindingView(view);
        bindingAction();

        setupChipGroups(view);
    }

    private void bindingView(View view) {
        cuisineGroup = view.findViewById(R.id.cuisine_group);
        flavorGroup = view.findViewById(R.id.flavor_group);
        intoleranceGroup = view.findViewById(R.id.intolerance_group);
        mealTypeGroup = view.findViewById(R.id.type_group);

        submitBtn = view.findViewById(R.id.submitBtn);
    }

    private void bindingAction() {
        submitBtn.setOnClickListener(this::handleSubmitPreferences);
    }

    private void handleSubmitPreferences(View view) {
        Toast.makeText(view.getContext(), String.valueOf(selectedCuisines.size()),
                Toast.LENGTH_SHORT).show();
    }

    private void setupChipGroups(View view) {
        Cuisine[] cuisines = Cuisine.values();
        for (Cuisine cuisine : cuisines) {
            cuisineGroup.addView(generatePreferenceChip(cuisine, cuisineGroup));
        }

        Flavor[] flavors = Flavor.values();
        for (Flavor flavor : flavors) {
            flavorGroup.addView(generatePreferenceChip(flavor, flavorGroup));
        }

        Intolerance[] intolerances = Intolerance.values();
        for (Intolerance intolerance : intolerances) {
            intoleranceGroup.addView(generatePreferenceChip(intolerance, intoleranceGroup));
        }

        MealType[] mealTypes = MealType.values();
        for (MealType mealType : mealTypes) {
            mealTypeGroup.addView(generatePreferenceChip(mealType, mealTypeGroup));
        }
    }

    private Chip generatePreferenceChip(FoodTag foodTag, ChipGroup chipGroup) {
        Chip preferenceChip =
                (Chip) getLayoutInflater().inflate(R.layout.custom_chip_layout, chipGroup, false);

        preferenceChip.setText(foodTag.getValue());
        preferenceChip
                .setOnCheckedChangeListener(
                        (compoundButton, checked) -> handlePreferenceChipSelect(foodTag, checked)
                );

        return preferenceChip;
    }

    // TODO: Refactor this with currying if possible
    private void handlePreferenceChipSelect(FoodTag foodTag, boolean checked) {
        // Cuisines
        if (foodTag instanceof Cuisine) {
            if (checked) {
                selectedCuisines.add((Cuisine) foodTag);
            } else {
                selectedCuisines.remove(foodTag);
            }

            return;
        }

        // Flavors
        if (foodTag instanceof Flavor) {
            if (checked) {
                selectedFlavors.add((Flavor) foodTag);
            } else {
                selectedFlavors.remove(foodTag);
            }

            return;
        }

        // Intolerances
        if (foodTag instanceof Intolerance) {
            if (checked) {
                selectedIntolerances.add((Intolerance) foodTag);
            } else {
                selectedIntolerances.remove(foodTag);
            }

            return;
        }

        // Meal types
        if (foodTag instanceof MealType) {
            if (checked) {
                selectedMealTypes.add((MealType) foodTag);
            } else {

                selectedMealTypes.remove(foodTag);
            }

            return;
        }
    }
}