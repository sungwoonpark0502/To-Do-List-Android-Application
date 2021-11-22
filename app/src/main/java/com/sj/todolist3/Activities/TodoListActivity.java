package com.sj.todolist3.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.sj.todolist3.Data;
import com.sj.todolist3.R;
import com.sj.todolist3.Todo;

import java.util.ArrayList;

public class TodoListActivity extends AppCompatActivity {

    static ListView todoList;
    EditText todoInsert;
    static ArrayList<String> todoListArray = new ArrayList<>();
    static ArrayAdapter adapter;
    Todo initTodoList;
    String email;
    Data data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todolist);

        todoList = findViewById(R.id.todoList);
        todoInsert = findViewById(R.id.insertTodo);

        email = (String) getIntent().getSerializableExtra("User Email");
        todoListArray = new ArrayList<>();

        data = new Data();
        data.dataInit();
        data.addNewUser(email);
        initTodoList = data.getData2(email);

        todoList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        Log.d("BEFORE ON COMPLETE", "before");
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, todoListArray);
        todoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedItem = todoList.getItemAtPosition(i).toString();
                Data.deleteData(selectedItem, email);
            }
        });
        createTask(todoList);
    }

    public static void setInitialTodoList(ArrayList<String> initTodoList) {
        for (int i = 0; i < initTodoList.size(); i++) {
            todoListArray.add(initTodoList.get(i));
        }
        todoList.setAdapter(adapter);
    }

    public void createTask(ListView todoListView) {
        todoInsert.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                if (event.getAction()!=KeyEvent.ACTION_DOWN) {
                    return true;
                }
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    // Perform action on key press
                    String todoItem = todoInsert.getText().toString();
                    if (todoItem.length() == 0) {
                        startToast("Please type an appropriate todo item");
                        return false;
                    }
                    todoInsert.getText().clear();
                    todoListArray.add(todoItem);
                    todoList.setAdapter(adapter);
                    data.saveData(todoItem, email);
                    return true;
                }
                return false;
            }
        });
    }

    public static void deleteCurrTotoList(String itemToDelete) {
        todoListArray.remove(itemToDelete);
        todoList.setAdapter(adapter);
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Toast a message
     * @param msg
     */
    private void startToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onBackPressed();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        // Here you want to show the user a dialog box
        Log.d("BACK BUTTON", " is pressed");
        new AlertDialog.Builder(this)
                .setTitle("Exiting the App")
                .setMessage("Are you sure?")
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // The user wants to leave - so dismiss the dialog and exit
                        FirebaseAuth.getInstance().signOut();
                        finish();
                        dialog.dismiss();
                    }
                }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // The user is not sure, so you can exit or just stay
                dialog.dismiss();
            }
        }).show();
    }


}