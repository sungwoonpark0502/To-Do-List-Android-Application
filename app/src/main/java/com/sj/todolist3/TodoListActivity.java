package com.sj.todolist3;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class TodoListActivity extends AppCompatActivity {

    ListView todoList;
    EditText todoInsert;
    ArrayList<String> todoListArray = new ArrayList<>();
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);

        todoList = findViewById(R.id.todoList);
        todoInsert = findViewById(R.id.insertTodo);

        Data data = new Data();
        data.dataInit();
        Todo initTodoList = data.getData();

//        todoList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
//        for (int i = 0; i < initTodoList.todoList.size(); i++) {
//            todoListArray.add(initTodoList.todoList.get(i));
//        }

        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, todoListArray);
        createTask(todoList);
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
                    if (todoItem.length() > 0) todoListArray.add(todoItem);
                    todoList.setAdapter(adapter);
                    return true;
                }
                return false;
            }
        });
    }
}