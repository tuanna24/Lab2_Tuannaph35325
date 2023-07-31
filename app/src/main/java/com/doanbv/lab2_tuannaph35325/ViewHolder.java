package com.doanbv.lab2_tuannaph35325;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.doanbv.lab2.R;

public  class ViewHolder extends RecyclerView.ViewHolder {
     TextView Title, Date;
     CheckBox cb_Status;
     ImageView btn_xoa, btn_update;
    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        Title = itemView.findViewById(R.id.tv_title_item);
        Date = itemView.findViewById(R.id.tv_Date_item);
        cb_Status = itemView.findViewById(R.id.cb_Status);
        btn_xoa = itemView.findViewById(R.id.img_cancel_item);
        btn_update = itemView.findViewById(R.id.img_Update_item);


    }
}
