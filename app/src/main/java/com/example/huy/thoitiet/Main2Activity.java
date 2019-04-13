package com.example.huy.thoitiet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Main2Activity extends AppCompatActivity {

    ImageView imgback;
    TextView txttenTP;
    ListView listview;
    CustomAdapter customAdapter;
    ArrayList<ThoiTiet> mangthoitiet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        AnhXa();
        Intent intent = getIntent();
        String thanhpho = intent.getStringExtra("name");
        if(thanhpho.equals(""))
        {
            Get7DayData("Hue");
        }
        else
        {
            Get7DayData(thanhpho);
        }
        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void AnhXa() {
        imgback = findViewById(R.id.imageviewBack);
        txttenTP = findViewById(R.id.textviewTenTP);
        listview = findViewById(R.id.listview);
        mangthoitiet =  new ArrayList<ThoiTiet>();
        customAdapter = new CustomAdapter(Main2Activity.this,mangthoitiet);
        listview.setAdapter(customAdapter);
    }

    private void Get7DayData(String data) {
        String url = "http://api.openweathermap.org/data/2.5/forecast?q="+data+"&units=metric&cnt=7&appid=648aaed47945fdd1c6f2750336ee6419";
        RequestQueue requestQueue = new Volley().newRequestQueue(Main2Activity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("ketqua2",response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject jsonObjectCity = jsonObject.getJSONObject("city");
                    String name = jsonObjectCity.getString("name");
                    txttenTP.setText(name);
                    JSONArray jsonArrayList = jsonObject.getJSONArray("list");
                    for(int i=0; i<jsonArrayList.length(); i++)
                    {
                        JSONObject jsonObjectlist = jsonArrayList.getJSONObject(i);
                        String ngay = jsonObjectlist.getString("dt");

                        long l =  Long.valueOf(ngay);
                        Date date = new Date(l*1000L);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE yyyy-MM-dd");
                        String Day = simpleDateFormat.format(date);

                        JSONObject jsonObjectMain = jsonObjectlist.getJSONObject("main");
                        String max = jsonObjectMain.getString("temp_max");
                        String min = jsonObjectMain.getString("temp_min");
                        Double a = Double.valueOf(max);
                        String NhietdoMax = String.valueOf(a.intValue());
                        Double b = Double.valueOf(min);
                        String NhietdoMin = String.valueOf(a.intValue());

                        JSONArray jsonArrayWeather = jsonObjectlist.getJSONArray("weather");
                        JSONObject jsonObjectWeather = jsonArrayWeather.getJSONObject(0);

                        String status = jsonObjectWeather.getString("description");
                        String icon = jsonObjectWeather.getString("icon");
                        mangthoitiet.add(new ThoiTiet(Day,status,icon,NhietdoMax,NhietdoMin));
                    }
                    customAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);
    }
}
