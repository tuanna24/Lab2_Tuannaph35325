package com.doanbv.lab2_tuannaph35325;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;

public class TodoDAO {

    Context context;
    DbHelper dbHelper;


    public TodoDAO(Context context, DbHelper dbHelper) {
        this.context = context;
        this.dbHelper = dbHelper;
    }

    public void insert(Todo todo) {
        SQLiteDatabase sql = dbHelper.getWritableDatabase();

        ContentValues ctv = new ContentValues();
        //ghép giá trị vào cột tương ứng
//                (ID Integer primary key, Title text, Content text, Date text,Type text,Status int)";
        ctv.put("Title", todo.getTitle());
        ctv.put("Content", todo.getContent());
        ctv.put("Date", todo.getDate());
        ctv.put("Type", todo.getType());
        ctv.put("Status", todo.getStatus());
        long kq = sql.insert("Todo", null, ctv);
        if (kq > 0) {
            Toast.makeText(context, "Them thanh cong", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Them khong thanh cong", Toast.LENGTH_SHORT).show();
        }
    }

    public void update(Todo todo) {

        SQLiteDatabase sql = dbHelper.getWritableDatabase();
        ContentValues ctv = new ContentValues();

        ctv.put("Title", todo.getTitle());
        ctv.put("Content", todo.getContent());
        ctv.put("Date", todo.getDate());
        ctv.put("Type", todo.getType());
        ctv.put("Status", todo.getStatus());

        long kq = sql.update("Todo", ctv, "ID = ?", new String[]{String.valueOf(todo.getID())});

        if (kq > 0) {
            Toast.makeText(context, "Update thanh cong", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Update khong thanh cong", Toast.LENGTH_SHORT).show();
        }
    }

    public void updateTodo(Integer id, boolean check) {
//int checkS = check ?1:0;
        int checkStatus;
        if (check == true) {
            checkStatus = 1;
        } else {
            checkStatus = 0;
        }
        SQLiteDatabase sql = dbHelper.getWritableDatabase();
        ContentValues ctv = new ContentValues();
        ctv.put("Status", checkStatus);
        long kq = sql.update("Todo", ctv, "ID = ?e", new String[]{String.valueOf(id)});

        if (kq > 0) {
            Toast.makeText(context, "Updatetodo thanh cong", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Updatetodo khong thanh cong", Toast.LENGTH_SHORT).show();
        }
    }

    public void delete(Todo todo) {
        SQLiteDatabase sql = dbHelper.getWritableDatabase();

        long kq = sql.delete("Todo", "ID =  ?", new String[]{String.valueOf(todo.getID())});

        if (kq > 0) {
            Toast.makeText(context, "Xoa Thanh cong", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Xoa khong thanh cong", Toast.LENGTH_SHORT).show();
        }

    }
    public ArrayList<Todo> getListTodo() {
        ArrayList<Todo> list = new ArrayList<>();
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        String getAll = "SELECT * FROM Todo";
        //Tham so thuw hai dung de dinh nghia danh sach cot minh muon chuy van
        // Lay tat ca thi de la null
        // Cursor : class dung de chua ket qua truy van tu DB
        database.beginTransaction();
        try{
            Cursor cursor = database.rawQuery(getAll, null);
            if(cursor.getCount() > 0){
                cursor.moveToFirst();

            }
            while(!cursor.isAfterLast()){
                list.add(new Todo(cursor.getInt(0), cursor.getString(1),cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getInt(5)));
                cursor.moveToNext();
        }
            database.setTransactionSuccessful();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }finally {
            database.endTransaction();
        }
        return list;
    }

    public boolean updateToDao (Todo todo) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Title", todo.getTitle());
        values.put("Content", todo.getContent());
        values.put("Date", todo.getDate());
        values.put("Type", todo.getType());

        int check = database.update("Todo", values, "id = ?", new String[]{String.valueOf(todo.getID())});
        return check != -1;
    }
}
