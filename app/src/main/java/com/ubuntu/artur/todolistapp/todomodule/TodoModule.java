package com.ubuntu.artur.todolistapp.todomodule;

import com.ubuntu.artur.todolistapp.entities.TodoItem;

import java.util.HashMap;
import java.util.UUID;

/**
 * @author artur
 *
 * Class represents TODOList application logic
 */
public class TodoModule {
    private HashMap<UUID, TodoItem> todoList;

    public TodoModule() {
        todoList = new HashMap<UUID, TodoItem>();
    }

    public boolean onTodoItemAdded(UUID id, TodoItem item) {
        todoList.put(id, item);
        return true;
    }

    public boolean onTodoItemDeleted(UUID id) {
        boolean isSuccessful = todoList.remove(id) != null;
        return isSuccessful;
    }

    public boolean onTodoItemStateChanged(UUID id, boolean isDone) {
        TodoItem item = todoList.get(id);
        if (item == null) {
            return false;
        }
        item.setDoneState(isDone);
        return true;
    }

    public Iterable<TodoItem> getTodoItems() {
        return todoList.values();
    }
}
