package com.example.huy.thoitiet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {
    Context context;
    ArrayList<ThoiTiet> arrayList;

    public CustomAdapter(Context context, ArrayList<ThoiTiet> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.dong_listview,null);

        ThoiTiet thoiTiet = arrayList.get(position);
        TextView txtDay = convertView.findViewById(R.id.textviewNgay);
        TextView txtStatus = convertView.findViewById(R.id.textviewTrangthai2);
        TextView txtMaxTemp = convertView.findViewById(R.id.textviewMax);
        TextView txtMinTemp = convertView.findViewById(R.id.textviewMin);
        ImageView imageTrangthai = convertView.findViewById(R.id.imageviewTrangthai);

        txtDay.setText(thoiTiet.Day);
        txtStatus.setText(thoiTiet.Status);
        txtMaxTemp.setText(thoiTiet.MaxTemp+"°C");
        txtMinTemp.setText(thoiTiet.MinTemp+"°C");

        Picasso.with(context).load("http://openweathermap.org/img/w"+thoiTiet.Image+".png").into(imageTrangthai);
        return convertView;
    }
}
