package com.example.careconsortium.util;

import com.example.careconsortium.BuildConfig;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;


public class FirestoreUtil {

    private static FirebaseFirestore mFirestore;
    private static final boolean sUseEmulators = false; //BuildConfig.DEBUG;

    public static FirebaseFirestore getDB() {
        // Get a reference to the restaurants collection
        if (mFirestore == null) {
            mFirestore = FirebaseFirestore.getInstance();

            // Connect to the Cloud Firestore emulator when appropriate. The host '10.0.2.2' is a
            // special IP address to let the Android emulator connect to 'localhost'.
            if (sUseEmulators) {
                mFirestore.useEmulator("10.0.2.2", 8080);
            }
        }

        return mFirestore;
    }

    public static CollectionReference getCollection(String name) {
        return getDB().collection(name);
    }
}
