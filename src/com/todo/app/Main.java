package com.todo.app;

import com.todo.app.common.TodoApp;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/***
 * @author Jatinder
 * @since 2020
 */
public class Main extends Application {

    /*application start with this method*/
    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
        primaryStage.setTitle("Todo Tasks");
        primaryStage.setScene(new Scene(root, 900, 700));
        primaryStage.show();
    }

    /*Init is used to initialize the application content if required */
    @Override
    public void init() throws Exception {
        TodoApp.getInstance().loadTodoItems();
    }

    /*Stop method is used to close the resources of application if any when application is closed*/
    @Override
    public void stop() throws Exception {
        TodoApp.getInstance().saveTodoItems();
    }

    /*Application is launched by the main method*/
    public static void main(String[] args) {
        launch(args);
    }
}
