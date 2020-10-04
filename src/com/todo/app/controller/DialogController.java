package com.todo.app.controller;

import com.todo.app.common.TodoApp;
import com.todo.app.model.Todo;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
/***
 * @author Jatinder
 * @since 2020
 */
public class DialogController {

    @FXML
    private TextField title;

    @FXML
    private TextArea description;

    @FXML
    private DatePicker dueDate;

    public Todo processTodoItemData(){
        TodoApp app = TodoApp.getInstance();
        Todo item = new Todo(app.getItemCount(), title.getText().trim(),description.getText().trim(),dueDate.getValue());
        app.addTodoItem(item);
        return item;
    }
}
