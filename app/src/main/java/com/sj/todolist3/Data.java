package com.sj.todolist3;

import android.util.Log;
import android.util.Pair;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class Data {

    private FirebaseFirestore db;
    private static String email;

    public static void setEmail(String email) {
        email = email;
    }

    public void dataInit() {
        db = FirebaseFirestore.getInstance();
    }

    public Todo getData() {
        Todo todoList = new Todo();
        ArrayList todoListArray = new ArrayList<>();
        db.collection("user")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String TAG = "EMAIL WITH DATA";
                                Log.d(TAG, document.getId() + " => " + document.getData());
//                                Map<String, Object> todoMap = document.getData();
//                                for (Map.Entry<String, Object> todoPair : todoMap.entrySet()) {
//                                    todoList.insert(todoPair.getValue().toString());
//                                }
                            }
                        } else {

                        }
                    }
                });
        return todoList;
    }

    public static void saveData() {

    }

}
