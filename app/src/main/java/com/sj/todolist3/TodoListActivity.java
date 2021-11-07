package com.sj.todolist3;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class TodoListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todolist);
//        ListView todoListView = (ListView)findViewById(R.id.thingsToDo);

    }

//    public void createTask(ListView todoListView){
//        TextView field = (TextView)findViewById(R.id.createTask);
//        field.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View view, int keyCode, KeyEvent event) {
//                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
//                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
//
//                }
//                return false;
//            }
//        });
//    }
}