package com.todo.app.controller;

import com.todo.app.common.TodoApp;
import com.todo.app.model.Todo;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.util.Callback;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class Controller {
    @FXML
    private ListView<Todo> todoListView;
    @FXML
    private TextArea todoDescriptionArea;
    @FXML
    private Label dueDateLabel;
    @FXML
    private Label todoTitle;
    @FXML
    private BorderPane mainWindow;


    private DateTimeFormatter dateTimeFormatter;

    private String formatedDate(LocalDate date){
        dateTimeFormatter = DateTimeFormatter.ofPattern("MMMM d, yyyy");
        return dateTimeFormatter.format(date);
    }

    /*This method will automatically get invoke when application start it will initialize the content in given fxml UI*/
    public void initialize(){
        /*add event listener on the list view and show first list view item by default and show in text area */
        todoListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Todo>() {
            @Override
            public void changed(ObservableValue<? extends Todo> observable, Todo oldValue, Todo newValue) {
                if(newValue !=null){
                    Todo item = todoListView.getSelectionModel().getSelectedItem();
                    todoTitle.setText(item.getTitle());
                    todoDescriptionArea.setText(item.getDescription());

                    dueDateLabel.setText(formatedDate(item.getDueDate()));
                }
            }
        });

        /*add list item to list view*/
        todoListView.setItems(TodoApp.getInstance().getTodoList());

        /*set selection mode to single*/
        todoListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        /*by default make first element of list view to be selected*/
        todoListView.getSelectionModel().selectFirst();

        /*create a cell factory to color some of the column having higher priority*/
        todoListView.setCellFactory(new Callback<ListView<Todo>, ListCell<Todo>>() {
            @Override
            public ListCell<Todo> call(ListView<Todo> param) {
                ListCell<Todo> todoListCell = new ListCell<Todo>(){
                    @Override
                    protected void updateItem(Todo item, boolean empty) {
                        super.updateItem(item, empty);
                        if(empty){
                            setText(null);
                        }else{
                            setText(item.getTitle());
                            if(item.getDueDate().isBefore(LocalDate.now().plusDays(1))){
                                setTextFill(Color.RED);
                            }else if(item.getDueDate().equals(LocalDate.now().plusDays(1))){
                                setTextFill(Color.GREEN);
                            }
                        }
                    }
                };
                return todoListCell;
            }
        });
    }

    @FXML
    public void showAddItemDialog(){
        Dialog<ButtonType> addItemDialog = new Dialog<>();
        addItemDialog.setTitle("Add Todo Item");
        addItemDialog.setHeaderText("Enter data to add new todo item.");
        addItemDialog.initOwner(mainWindow.getScene().getWindow());
        FXMLLoader uiLoader = new FXMLLoader();
        uiLoader.setLocation(getClass().getResource("../add-dialog.fxml"));

        try{
            // Load Dialog UI
            Parent addDialog = uiLoader.load();
            //Add to the screen
            addItemDialog.getDialogPane().setContent(addDialog);
        }catch (IOException e){
            e.printStackTrace();
        }

        // Add OK and Cancel Button
        addItemDialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        addItemDialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        //Check which button is clicked
        Optional<ButtonType> result = addItemDialog.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            DialogController dialogController = uiLoader.getController();
            Todo addedItem = dialogController.processTodoItemData();

            //add todoItem in left sidebar as well but explicitly
//            todoListView.getItems().add(addedItem);

            //select the newly added item by default after adding
            todoListView.getSelectionModel().select(addedItem);
        }
    }

    @Deprecated
    /***
     * Description : This method is not required we have implement when creating list view
     */
    public void onClickHandler(){
        Todo item = todoListView.getSelectionModel().getSelectedItem();
        todoTitle.setText(item.getTitle());
        todoDescriptionArea.setText(item.getDescription());
        dueDateLabel.setText(formatedDate(item.getDueDate()));
    }
}
