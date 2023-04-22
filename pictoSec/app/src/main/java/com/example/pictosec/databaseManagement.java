package com.example.pictosec;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.*;
import com.google.firebase.ktx.Firebase;

import java.security.spec.InvalidKeySpecException;
import java.util.*;

public class databaseManagement {

        public static FirebaseFirestore db;
        Task<QuerySnapshot> query;
        private static List<String> list;
        private static int i;
        public static ArrayList<String> passwords;

        public static ArrayList<String> contexts;

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
                Task<DocumentSnapshot> task2 = db.collection("userPasswords")
                        .document("ppurushothaman1").get();
                while (!task2.isComplete()) ;
                DocumentSnapshot document2 = task.getResult();
                contexts = (ArrayList<String>) document2.get("pContext");
        }

        public static void register(String username, String password) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeySpecException {
                String pass = password;
                String user = username;
                SecureRandom random = new SecureRandom();
                byte[] salt = new byte[64];
                random.nextBytes(salt);
                pass = passwordHash.createHash(password, salt);
                Blob blob = Blob.fromBytes(salt);
                ArrayList<String> passwords = new ArrayList<>();
                ArrayList<String> pContext = new ArrayList<>();
                Map<String, Object> data = new HashMap<>();
                data.put("password", pass);
                data.put("salt", blob);
                db.collection("users").document(username).set(data);
                Map<String, Object> data2 = new HashMap<>();
                data2.put("username", user);
                data2.put("passwords", passwords);
                data2.put("pContext", pContext);
                db.collection("userPasswords").document(username).set(data2);
        }

        public static int addPassword(String username, String str, String context) {
                db.collection("userPasswords")
                        .document(users.username).update("passwords", FieldValue.arrayUnion(str));
                db.collection("userPasswords")
                        .document(users.username).update("pContext", FieldValue.arrayUnion(context));
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

        public static boolean getLogin(String username, String password) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeySpecException {
                DocumentReference docRef = db.collection("users").document(username);
                Task<DocumentSnapshot> task = docRef.get();
                while (!task.isComplete()) ;
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                        String str2;
                        String str1 = (String) document.get("password");
                        Blob blob = document.getBlob("salt");
                        byte[] arr = blob.toBytes();
                        str2 = passwordHash.createHash(password, arr);
                        if (str2.compareTo(str1) == 0) {
                                return true;
                        }
                } else {
                        return false;
                }
                return false;
        }

        public static String[] retrievePassword(String username) {
                if(passwords.size() == 0){
                        return null;
                }
                String pass;
                String context;
                String[] foo = new String[2];
                if(i == passwords.size()){
                        i = 0;
                }
                context = contexts.get(i);
                pass = passwords.get(i);
                foo[0] = context;
                foo[1] = pass;
                i++;
                return foo;
        }
}
