package com.sj.todolist3;

import java.util.ArrayList;

public class Todo {

    ArrayList<String> todoList;

    public void insert(String todoItem) {
        todoList.add(todoItem);
    }

    public String delete(String todoItem) {
        for (String currItem : todoList){
            if (currItem.equals(todoItem)) {
                todoList.remove(todoItem);
                return todoItem;
            }
        }
        return "";
    }

}
