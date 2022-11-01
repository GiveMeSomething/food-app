package com.example.foodapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.foodapp.firebase.UserRepository;
import com.example.foodapp.firebase.entity.UserPreference;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserViewModel extends AndroidViewModel {
    private final UserRepository userRepository;
    private MutableLiveData<UserPreference> userPreferences = null;

    public UserViewModel(@NonNull Application application) {
        super(application);

        FirebaseUser authInfo = FirebaseAuth.getInstance().getCurrentUser();
        if (authInfo == null || authInfo.getEmail() == null || authInfo.getEmail().trim().equals("")) {
            // TODO: Handle error here
            return;
        }
        userRepository = UserRepository.getCurrentUser(authInfo.getEmail());

        userPreferences = new MutableLiveData<>();
        userRepository.getUserPreference(userPreferences::postValue);
    }
}
