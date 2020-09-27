package com.todo.app.common;

import com.todo.app.model.Todo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;

public class TodoApp {
    private static TodoApp instance = new TodoApp();
    private String todoFileName = "todo-list.txt";
    private DateTimeFormatter format;
    private ObservableList<Todo> todoList;

    /*Private constructor to make singleton class*/
    private TodoApp(){
        format = DateTimeFormatter.ofPattern("yyyy-MM-dd");//dd-MM-yyyy
    }

    /*method to get instance of class*/
    public static TodoApp getInstance(){
        return instance;
    }

    public ObservableList<Todo> getTodoList() {
        return todoList;
    }

    public void setTodoList(ObservableList<Todo> todoList) {
        this.todoList = todoList;
    }

    /***
     * description: Number return by this method will act as new ID for TodoItem
     * @return size of the list will be returned by adding 1 into the size of list
     */
    public int getItemCount(){
        return todoList.size()+1;
    }

    public void addTodoItem(Todo todoItem){
        todoList.add(todoItem);
    }

    public void loadTodoItems(){
        todoList = FXCollections.observableArrayList();
        Path filePath = Paths.get(todoFileName);
        String input;
        try(BufferedReader reader = Files.newBufferedReader(filePath)){
            while ((input = reader.readLine()) !=null){
                String[] data =  input.split(",");
                todoList.add(new Todo(Integer.parseInt(data[0]), data[1], data[2], LocalDate.parse(data[3],format)));
            }
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    /***
     * Description: Save some data in the file
     */
    public void saveTodoItems(){
        Path filePath = Paths.get(todoFileName);
        try(BufferedWriter writer = Files.newBufferedWriter(filePath)){
            Iterator<Todo> iterator = todoList.listIterator();
            while (iterator.hasNext()){
                Todo item = iterator.next();
                String data = String.format("%d,%s,%s,%s",item.getId(), item.getTitle(), item.getDescription(), item.getDueDate());
                writer.write(data);
                writer.newLine();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /***
     * Description: Delete  TodoItem from the list
     * @param item
     */
    public void deleteTodoItem(Todo item){
        todoList.remove(item);
    }

}
