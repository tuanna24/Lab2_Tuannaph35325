package com.doanbv.lab2_tuannaph35325;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.doanbv.lab2.R;

import java.util.ArrayList;

public class TodoAdapter extends RecyclerView.Adapter<ViewHolder> {

    Activity activity;
    ArrayList<Todo> list;
    SetClickDelete setClickDelete;
    TodoDAO todoDAO;

    public void setOnClickDelete() {

    }

    public TodoAdapter(Activity activity, ArrayList<Todo> list) {
        this.activity = activity;
        this.list = list;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Hàm này là hàm tạo view

        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.item_todo, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        TodoDAO todoDAO = new TodoDAO(activity, new DbHelper(activity));
        //Set du lieu tung item len recyclerview
        holder.Title.setText(list.get(position).getTitle());
        holder.Date.setText(list.get(position).getDate());
//        Toast.makeText(activity, ""+list.get(0).getTitle(), Toast.LENGTH_SHORT).show();
        if (list.get(position).getStatus() == 1) {
            holder.cb_Status.setChecked(true);
            holder.cb_Status.setEnabled(false);
            holder.Title.setPaintFlags(holder.Title.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        } else {
            holder.cb_Status.setChecked(false);
            holder.cb_Status.setEnabled(true);
            holder.Title.setPaintFlags(holder.Title.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) activity).getPosition(position);
                ((MainActivity) activity).setTextEdittext(list.get(position));
            }
        });
        holder.cb_Status.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (holder.cb_Status.isChecked()) {
                    todoDAO.updateTodo(list.get(position).getID(), true);
                    holder.cb_Status.isChecked();
                    holder.Title.setPaintFlags(holder.Title.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    holder.cb_Status.setEnabled(false);
                    list = todoDAO.getListTodo();
                    notifyDataSetChanged();
                }
            }
        });
        holder.btn_xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(todoDAO.context);
                builder.setTitle("Cảnh Báo");
//                builder.setIcon(R.drawable.ic_launcher_background);
                builder.setMessage("Bạn có chắc muốn xóa công việc này không?");

                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        todoDAO.delete(list.get(holder.getAdapterPosition()));
                        list.clear();
                        list.addAll(todoDAO.getListTodo());
                        notifyItemRemoved(holder.getAdapterPosition());
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
        holder.btn_update.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(todoDAO.context);
            LayoutInflater inflater = ((Activity) todoDAO.context).getLayoutInflater();
            View view1 = inflater.inflate(R.layout.dialog, null);
            builder.setView(view1);

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
            EditText edtTitle1 = view1.findViewById(R.id.edt_Title1);
            EditText edtContent1 = view1.findViewById(R.id.edt_Conten1);
            EditText edtDate1 = view1.findViewById(R.id.edt_Date1);
            EditText edtType1 = view1.findViewById(R.id.edt_Type1);

            Button btnUpdate = view1.findViewById(R.id.btn_update);
            Button btnCancel = view1.findViewById(R.id.btn_cancel);

            edtTitle1.setOnClickListener(view23 -> {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(activity);
                alertBuilder.setTitle("Chọn mức độ khó của công việc !");
                String[] doKho = {"Kho", "Tung Binh", "De"};
                alertBuilder.setItems(doKho, (dialogInterface, i) -> {
                    edtTitle1.setText(doKho[i]);
                });
                builder.create();
                alertDialog.show();
            });

            btnUpdate.setOnClickListener(view2 -> {
                String Title = edtTitle1.getText().toString();
                String Context = edtContent1.getText().toString();
                String Date = edtDate1.getText().toString();
                String Type = edtType1.getText().toString();

                Todo todo = new Todo(list.get(position).getID(), Title, Context, Date, Type, list.get(position).getStatus());
                todoDAO.updateToDao(todo);
                list.set(position, todo);
                notifyItemChanged(position);
                notifyItemRangeChanged(position, list.size());
            });
            btnCancel.setOnClickListener(view22 -> {
                alertDialog.dismiss();
            });

        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }
}
