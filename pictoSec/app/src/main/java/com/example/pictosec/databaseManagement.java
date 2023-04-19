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

        public static FirebaseFirestore db;
        Task<QuerySnapshot> query;
        private static List<String> list;
        private static int i;
        public static ArrayList<String> passwords;

        public databaseManagement() {
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
                while (!task.isComplete()) ;
                DocumentSnapshot document = task.getResult();
                passwords = (ArrayList<String>) document.get("passwords");
        }

        public static void register(String username, String password) {
                String pass = password;
                String user = username;
                ArrayList<String> passwords = new ArrayList<>();
                ArrayList<String> pContext = new ArrayList<>();
                Map<String, Object> data = new HashMap<>();
                data.put("password", pass);
                db.collection("users").document(username).set(data);
                Map<String, Object> data2 = new HashMap<>();
                data2.put("username", user);
                data2.put("passwords", passwords);
                data2.put("pContext", pContext);
                db.collection("userPasswords").document(username).set(data2);
        }

        public static int addPassword(String username, String str) {
                db.collection("userPasswords")
                        .document("ppurushothaman1").update("passwords", FieldValue.arrayUnion(str));
                return 1;
        }

        public static boolean getUserQuery(String username) {
                DocumentReference docRef = db.collection("users").document(username);
                Task<DocumentSnapshot> task = docRef.get();
                while (!task.isComplete()) ;
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                        return true;
                } else {
                        return false;
                }
        }

        public static boolean getLogin(String username, String password) {
                DocumentReference docRef = db.collection("users").document(username);
                Task<DocumentSnapshot> task = docRef.get();
                while (!task.isComplete()) ;
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                        String str = (String) document.get("password");
                        if (str.compareTo(password) == 0) {
                                return true;
                        }
                } else {
                        return false;
                }
                return false;
        }

        public static String retrievePassword(String username) {
                if(passwords.size() == 0){
                        return null;
                }
                String str;
                if(i == passwords.size()){
                        i = 0;
                }
                str = passwords.get(i);
                i++;
                return str;
        }
}
