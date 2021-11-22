package com.sj.todolist3;

import android.util.Log;
import android.util.Pair;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.sj.todolist3.Activities.TodoListActivity;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Data {

    private static FirebaseFirestore db;
    private static Map<String, Object> dataSet = new HashMap<>();

    public void dataInit() {
        db = FirebaseFirestore.getInstance();
    }

    public static boolean isCurrUser(String email) {
        final boolean[] isCurrUser = {false};

        DocumentReference docRef = db.collection(email).document("INFO");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    isCurrUser[0] = true;
                } else {
                    Map<String, Object> data = new HashMap<>();
                    data.put("email", email);
                    db.collection(email).document("INFO").set(data);
                }
            }
        });
        return isCurrUser[0];
    }

    public void addNewUser(String email) {
        Map<String, Object> data = new HashMap<>();
        data.put("email", email);
        db.collection(email).document("INFO").set(data);
    }

    public Todo getData2(String email) {
        Log.d("EMAIL:", email);
        Todo todoList = new Todo();
        String TAG = "Data 2";
        ArrayList todoListArray = new ArrayList<>();
        CollectionReference docRef = db.collection(email);
        docRef.orderBy("date", Query.Direction.ASCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d(TAG, document.getId() + " => " + document.getData());
                        String item = document.getId();
                        String date = document.get("date").toString();
                        todoListArray.add(item);
                    }
                    TodoListActivity.setInitialTodoList(todoListArray);
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });
        return todoList;
    }

    public static void saveData(String newData, String email) {
        LocalDateTime currTime = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            currTime = LocalDateTime.now();
        }
        Map<String, Object> timeStamp = new HashMap<>();
        timeStamp.put("date", FieldValue.serverTimestamp());
        dataSet.put(currTime.toString(), newData);
        String TAG = "save data:";

        db.collection(email).document(newData).set(timeStamp);

    }

    public static void deleteData(String dataToDelete, String email) {
        String TAG = "DELETE: ";
        db.collection(email).document(dataToDelete)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error deleting document", e);
                    }
                });
        TodoListActivity.deleteCurrTotoList(dataToDelete);
    }

}
