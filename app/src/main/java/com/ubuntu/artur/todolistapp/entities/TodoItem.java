package com.ubuntu.artur.todolistapp.entities;

import java.util.UUID;

/**
 * @author artur
 *
 * Entity class represents TO DO list item
 */
public class TodoItem {
    private UUID id;
    private String todoText;
    private boolean isDone;

    public TodoItem(String todoText) {
        this.todoText = todoText;
        id = UUID.randomUUID();
        isDone = false;
    }

    public void setDoneState(boolean isDone) {
        this.isDone = isDone;
    }

    public UUID getId() {
        return id;
    }

    public String getTodoText() {
        return todoText;
    }

    public boolean isDone() {
        return isDone;
    }

    @Override
    public String toString() {
        return todoText;
    }
}
