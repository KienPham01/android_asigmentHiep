package com.example.huy.thoitiet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    EditText edtTimkiem;
    Button btnTimkiem,btnNext;
    TextView txtThanhpho,txtQuocgia,txtNhietdo,txtTrangthai,txtMay,txtGio,txtDoam,txtDay;
    ImageView imgIcon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AnhXa();
        GetCurrenWeatherData("Hanoi");
        btnTimkiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String thanhpho = edtTimkiem.getText().toString();
                if(thanhpho.equals(""))
                {
                    GetCurrenWeatherData("Hue");
                }
                else
                {
                    GetCurrenWeatherData(thanhpho);
                }

            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Main2Activity.class);
                String thanhpho = edtTimkiem.getText().toString();
                intent.putExtra("name",thanhpho);
                startActivity(intent);
            }
        });
    }
    public void GetCurrenWeatherData(String data)
    {
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        String url ="http://api.openweathermap.org/data/2.5/weather?q="+data+"&units=metric&APPID=648aaed47945fdd1c6f2750336ee6419";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("ketqua",response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String day = jsonObject.getString("dt");
                            String name = jsonObject.getString("name");
                            txtThanhpho.setText("Tên thành phố: "+name);

                            long l =  Long.valueOf(day);
                            Date date = new Date(l*1000L);
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE yyyy-MM-dd HH-mm-ss");
                            String Day = simpleDateFormat.format(date);
                            txtDay.setText(Day);

                            JSONArray jsonArray = jsonObject.getJSONArray("weather");
                            JSONObject jsonObjectWeather = jsonArray.getJSONObject(0);

                            String Trangthai = jsonObjectWeather.getString("main");
                            String icon = jsonObjectWeather.getString("icon");

                            Picasso.with(MainActivity.this).load("http://openweathermap.org/img/w/"+icon+".png").into(imgIcon);
                            txtTrangthai.setText(Trangthai);

                            JSONObject jsonObjectMain = jsonObject.getJSONObject("main");
                            String nhietdo = jsonObjectMain.getString("temp");
                            String doam = jsonObjectMain.getString("humidity");

                            Double a = Double.valueOf(nhietdo);
                            String Nhietdo = String.valueOf(a.intValue());
                            txtNhietdo.setText(Nhietdo+"°C");
                            txtDoam.setText(doam+"%");

                            JSONObject jsonObjectWind = jsonObject.getJSONObject("wind");
                            String gio = jsonObjectWind.getString("speed");
                            txtGio.setText(gio+"m/s");

                            JSONObject jsonObjectCloud = jsonObject.getJSONObject("clouds");
                            String may = jsonObjectCloud.getString("all");
                            txtMay.setText(may+"%");

                            JSONObject jsonObjectSys = jsonObject.getJSONObject("sys");
                            String quocgia = jsonObjectSys.getString("country");
                            txtQuocgia.setText("Tên quốc gia: "+ quocgia);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Tên thành phố nhập chưa chính xác", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(stringRequest);
    }
    private void AnhXa()
    {
        edtTimkiem =  findViewById(R.id.edittextTimkiem);
        btnTimkiem = findViewById(R.id.buttonTimkiem);
        btnNext = findViewById(R.id.buttonNextday);
        txtThanhpho = findViewById(R.id.textviewThanhPho);
        txtQuocgia = findViewById(R.id.textviewNuoc);
        txtNhietdo = findViewById(R.id.textviewTemp);
        txtTrangthai = findViewById(R.id.textviewTrangthai);
        txtMay = findViewById(R.id.textviewMay);
        txtGio = findViewById(R.id.textviewGio);
        txtDoam = findViewById(R.id.textviewDoam);
        txtDay = findViewById(R.id.textviewDay);
        imgIcon = findViewById(R.id.imageviewIcon);
    }
}
