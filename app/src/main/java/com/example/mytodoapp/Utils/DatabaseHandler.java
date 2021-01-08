package com.example.mytodoapp.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.mytodoapp.Model.ToDoModel;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    //TASK, ID, STATUS are key values
    private static final int VERSION = 1;   //version of the database
    private static final String NAME = "toDoListDatabase";  //name of database
    private static final String TODO_TABLE = "todo";    //database table name
    private static final String ID = "id";  //database column name
    private static final String TASK = "task";  //this is the actual data being stored
                                                //This will also serve as a column
    private static final String STATUS = "status";
    private static final String CREATE_TODO_TABLE = "CREATE TABLE " + TODO_TABLE + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                                + TASK + "TEXT, " + STATUS + " INTEGER)";
                    //CREATE_TODO_TABLE is the query name,
                    //Query is going to be used to request the information from the database
    private SQLiteDatabase db;  //reference to the SQLite Database

    public DatabaseHandler(Context context) {   //Context is a class
        super(context, NAME, null, VERSION);
        //(context, Name of the database, Not sure, VERSION of database)
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_TODO_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        //drop the existing/older table
        db.execSQL("DROP TABLE IF EXISTS " + TODO_TABLE);
        //create tables again
        onCreate(db);   //method that creates a new table for us
    }

    public void openDatabase(){ //this will open the table
        //needs to be called within the main activity
        db = this.getWritableDatabase();
    }

    public void insertTask(ToDoModel task){ //contains the text, status, id
        ContentValues cv = new ContentValues(); //storing the task in this object
        //ContentValues is used to store a set of value that the ContentResolver can process
        cv.put(TASK, task.getTask());   //(key value, String)
        cv.put(STATUS, 0);  //(Key Value, Status num);
        db.insert(TODO_TABLE, null, cv);
        //(Database name, null to insert the whole row, content values)
        //ContentValues does the raw SQL Queries for us, such as execSQL
    }

    public List<ToDoModel> getAllTasks(){
        List<ToDoModel> taskList = new ArrayList<>();
        Cursor cur = null;
        //Cursor is an interface that provides read-write to the result returned by
        //a database query
        db.beginTransaction();
        try{
            cur = db.query(TODO_TABLE, null, null, null, null, null, null, null);
            //returns all the rows from the D.B. without any criteria
            if(cur != null){    //assuming that the query returned data
                if(cur.moveToFirst()){  //moves cursor to the first row/object
                    do{
                        ToDoModel task = new ToDoModel();
                        //cur.getColumnIndex() returns the data from that specific COLUMN
                        task.setId(cur.getInt(cur.getColumnIndex(ID))); //gets the ID from the cursor
                        task.setTask(cur.getString(cur.getColumnIndex(TASK))); //gets the Task from the curser
                        task.setStatus(cur.getInt(cur.getColumnIndex(STATUS)));
                        taskList.add(task);
                    }while(cur.moveToNext());   //while the cursor is not empty
                }
            }
        } finally {
            db.endTransaction();
            cur.close();
        }
        return taskList;
    }

    public void updateStatus(int id, int status){
        //we will get it from the recycler view, it should contain the params
        ContentValues cv = new ContentValues();
        cv.put(STATUS, status);
        db.update(TODO_TABLE, cv, ID + "=?", new String[] {String.valueOf(id)});
        // String.valueOf(id) converts int to string, and stores into new String[]
        // (Table Name, ContentValues, 'Where Clause' = ID
        // the '?' is for formating
        // This will update the task
    }

    public void updateTask(int id, String task){
        ContentValues cv = new ContentValues();
        cv.put(TASK, task);//adds a value to the set | (String key, Value)
        db.update(TODO_TABLE, cv, ID + "=?", new String[]{String.valueOf(id)});
    }

    public void deleteTask(int id){
        db.delete(TODO_TABLE, ID + "=?", new String[] {String.valueOf(id)});
    }
}
