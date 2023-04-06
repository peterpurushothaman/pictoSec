package com.example.pictosec;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.*;
import com.google.firebase.ktx.Firebase;

import java.util.*;

public class databaseManagement {

        private static FirebaseFirestore db;
        Task<QuerySnapshot> query;
        private static List<String> list;
        private static int i;
        private static ArrayList<String> passwords;

        public databaseManagement(){
                i = 0;
                db = FirebaseFirestore.getInstance();
                query = db.collection("userPasswords").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                        list = new ArrayList<>();
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                                list.add(document.getId());
                                        }
                                        Log.d(TAG, list.toString());
                                } else {
                                        Log.d(TAG, "Error getting documents: ", task.getException());
                                }
                        }
                });
                Task<DocumentSnapshot> task = db.collection("userPasswords")
                        .document("ppurushothaman1").get();
                while(!task.isComplete());
                DocumentSnapshot document = task.getResult();
                passwords = (ArrayList<String>) document.get("passwords");
        }

        public static int addPassword(String username, String str){
                //add a new list if the user is new, ignore this implementation for now, this will change
                //when i implement the login feature as the login will do this, this is simply to test
                if(!list.contains(username)) {
                        List<String> passwords = new ArrayList<>();
                        Map<String, Object> data = new HashMap<>();
                        data.put("username", username);
                        data.put("passwords", passwords);
                        db.collection("userPasswords")
                                .document("ppurushothaman1").set(data);
                }
                db.collection("userPasswords")
                        .document("ppurushothaman1").update("passwords", FieldValue.arrayUnion(str));
                return 1;
        }
        public static String retrievePassword(String username) {
                String str;
                if(i == 11){
                        i = 0;
                }
                str = passwords.get(i);
                i++;
                return str;
        }
}
