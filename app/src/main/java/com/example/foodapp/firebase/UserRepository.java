package com.example.foodapp.firebase;

import androidx.annotation.NonNull;

import com.example.foodapp.firebase.entity.UserPreference;
import com.example.foodapp.viewmodel.utils.MD5Util;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class UserRepository {

    private static UserRepository instance;

    private final FirebaseDatabase database;

    private final String email;

    private UserRepository(String email) {
        database =
                FirebaseDatabase.getInstance(FirebaseConfig.FIREBASE_REALTIME_DATABASE_URL);
        this.email = email;
    }

    public synchronized static UserRepository getCurrentUser(String email) {
        if (instance == null) {
            instance = new UserRepository(email);
        }

        return instance;
    }

    public void setUserPreference(UserPreference userPreference) {
        String emailHash = MD5Util.md5Hex(this.email);
        if(emailHash == null) {
            return;
        }

        DatabaseReference userReference = database.getReference("users").child(emailHash);
        Map<String, Object> userUpdate = new HashMap<>();
        userUpdate.put("preferences", userPreference);
        userReference.updateChildren(userUpdate);
    }

    public void getUserPreference(Consumer<UserPreference> listener) {
        String emailHash = MD5Util.md5Hex(this.email);
        if(emailHash == null) {
            return;
        }

        DatabaseReference userReference = database.getReference("users").child(emailHash);
        userReference.child("preferences").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isComplete()) {
                    return;
                }

                DataSnapshot snapshot = task.getResult();

                if (!snapshot.exists()) {
                    return;
                }

                UserPreference userPreference = snapshot.getValue(UserPreference.class);
                listener.accept(userPreference);
            }
        })
    }
}
