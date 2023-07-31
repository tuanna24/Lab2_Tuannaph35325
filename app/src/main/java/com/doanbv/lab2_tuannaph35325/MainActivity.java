package com.doanbv.lab2_tuannaph35325;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.doanbv.lab2.R;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    EditText edt_Title, edt_Content, edt_Date, edt_Type;
    Button btn_add;
    ArrayList<Todo> list = new ArrayList<>();
    TodoAdapter todoAdapter;
    RecyclerView recyclerView;
    DbHelper dbHelper;
    TodoDAO todoDAO;
    private String[] DoKho = new String[]{"Dễ", "Bình thường", "Khó"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edt_Title = findViewById(R.id.edt_Title);
        edt_Content = findViewById(R.id.edt_Conten);
        edt_Date = findViewById(R.id.edt_Date);
        edt_Type = findViewById(R.id.edt_Type);
        btn_add = findViewById(R.id.btn_ADD);

        recyclerView = findViewById(R.id.rv_list);

        //Muốn đổ dữ liệu lên recyclerview thì phải có dữ liệu từ:
        // Arraylist và Layout(VD : LeanLayout, ContranLayout)
        // Arraylist lấy dữ liệu từ Database
        dbHelper = new DbHelper(MainActivity.this);
        todoDAO = new TodoDAO(MainActivity.this, dbHelper);

        Todo todo = new Todo(new Random().nextInt(), "Hoc JAVA", "Hoc Java Co ban", "12/12/2120", "de", 0);
        todoDAO.insert(todo);

        //Lấy dữ liệu từ DataBase
        list = todoDAO.getListTodo();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        todoAdapter = new TodoAdapter(MainActivity.this, list);
        recyclerView.setAdapter(todoAdapter);


        edt_Type.setOnClickListener(view -> {
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MainActivity.this);
            alertBuilder.setTitle("Chọn mức độ khó của công việc !");
            alertBuilder.setItems(DoKho, (dialogInterface, i) -> {
                edt_Type.setText(DoKho[i]);
            });
            AlertDialog alertDialog = alertBuilder.create();
            alertDialog.show();
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int id;
                String Title = edt_Title.getText().toString().trim();
                String Content = edt_Content.getText().toString().trim();
                String Date = edt_Date.getText().toString().trim();
                String Type = edt_Type.getText().toString().trim();
                int Status;
                if (Type.equals("De")) {
                    Status = 1;
                } else {
                    Status = 0;
                }

                Todo todo1 = new Todo(new Random().nextInt(), Title, Content, Date, Type, Status);
                list.add(todo1);
                todoAdapter.notifyItemChanged(position);
            }
        });
    }

    int position;

    public void getPosition(int viTri) {
        position = viTri;
    }

    public void setTextEdittext(Todo todo) {
        edt_Type.setText(todo.getType());
        edt_Content.setText(todo.getContent());
        edt_Date.setText(todo.getDate());
        edt_Type.setText(todo.getType());
    }
}