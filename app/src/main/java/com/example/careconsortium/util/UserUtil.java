package com.example.careconsortium.util;

import androidx.annotation.NonNull;

import com.example.careconsortium.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;

public class UserUtil {
    public static User currentUser;
    public static String user_id;
    public static String user_document_id;
    public static boolean initialized = false;

    public static boolean checkLevelPlayed(String topic, int level) {
        HashMap<String, Integer> levels_played = currentUser.getLevels_played();

        if (levels_played.containsKey(topic))
            return level <= levels_played.get(topic);
        else {
            levels_played.put(topic, 0);
            return false;
        }
    }

    public static boolean checkLevelLocked(String topic, int level) {
        HashMap<String, Integer> levels_played = currentUser.getLevels_played();

        if (levels_played.containsKey(topic))
            return level > (levels_played.get(topic) + 1);
        else {
            levels_played.put(topic, 0);
            return level > 1;
        }
    }

    public static void updateLevelPlayed(String topic, int level) {
        HashMap<String, Integer> levels_played = currentUser.getLevels_played();

        levels_played.put(topic, level);
    }

    public static void update_user_to_database() {
        FirestoreUtil.getCollection("user").document(user_document_id).set(currentUser);
    }

    public static void initializeUser(String userid) {
        if(initialized)
            return;

        user_id = userid;
        FirestoreUtil.getCollection("user").whereEqualTo("user_id", user_id).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                boolean found = false;
                if(task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        user_document_id = document.getId();
                        currentUser = document.toObject(User.class);
                        found = true;
                        break;
                    }

                    if (! found) {
                        currentUser = new User();
                        currentUser.setCurrent_score(0);
                        currentUser.setUser_id(user_id);
                        currentUser.setLevels_played(new HashMap<String, Integer>());

                        DocumentReference user_document = FirestoreUtil.getCollection("user").document();
                        user_document_id = user_document.getId();
                        user_document.set(currentUser);
                    }
                    initialized = true;
                }
            }
        });
    }
}
