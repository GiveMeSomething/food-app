package com.example.foodapp.ui;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.GestureDetectorCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.foodapp.R;
import com.google.firebase.FirebaseApp;
import com.example.foodapp.databinding.ActivityMainBinding;
import com.example.foodapp.ui.login.LoginFragment;
import com.example.foodapp.ui.sign_up.SignUpFragment;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private GestureDetectorCompat gestureDetectorCompat;
    private ActivityMainBinding binding;
    private FirebaseAuth firebaseAuth;

    private final LoginFragment loginFragment = new LoginFragment();
    private final SignUpFragment signUpFragment = new SignUpFragment();
    private final RecipeFragment recipeFragment = new RecipeFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupWindow();
        setupFragments();
        initFirebase();

        testApi();

        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, recipeFragment)
                    .commit();
        } else {
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, signUpFragment)
                        .commit();
            }
        }
    }

    private void setupWindow() {
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.black));
    }

    private void setupFragments() {
        loginFragment.setOnLoginSuccess(() -> {
            Utils.clearAllFragment(this);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, recipeFragment, RecipeFragment.class.getCanonicalName()).commit();
        });

        signUpFragment.setShowLogin(() -> {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, loginFragment, SignUpFragment.class.getCanonicalName()).addToBackStack(SignUpFragment.class.getCanonicalName()).commit();
        });

        signUpFragment.setShowHome(() -> {
            Utils.clearAllFragment(this);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, recipeFragment, RecipeFragment.class.getCanonicalName()).commit();
        });
    }

    private void testApi() {
        UserViewModel userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        UserPreference userPreference = new UserPreference();

        userViewModel.setUserPreferences(userPreference);
        userViewModel.fetchUserPreferences();
    }

    private void initFirebase() {
        FirebaseApp.initializeApp(this);
    }
}