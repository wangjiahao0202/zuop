package com.example.yindao;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.yindao.shipeiqi.Xin;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class FangwuShu extends AppCompatActivity {
    RecyclerView rcv;
    Xin xin;
    ImageView fan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fangwu_shu);
        fan = findViewById(R.id.fan);
        fan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(FangwuShu.this,MainActivity.class);
                startActivity(intent);
            }
        });
        rcv = findViewById(R.id.rcv);
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url("http://192.168.10.36/rest/house/listUserAllHouses")
                .method("POST", body)
                .addHeader("authorization", LoginMainActivity.token1)
                .build();
       client.newCall(request).enqueue(new Callback() {
           @Override
           public void onFailure(@NotNull Call call, @NotNull IOException e) {

           }

           @Override
           public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
               String string = response.body().string();
               try {
                   JSONObject jsonObject = new JSONObject(string);
                   JSONArray data = jsonObject.getJSONArray("data");
                   xin = new Xin(FangwuShu.this,data);
                   runOnUiThread(new Runnable() {
                       @Override
                       public void run() {
                           rcv.setLayoutManager(new LinearLayoutManager(FangwuShu.this));
                           rcv.setAdapter(xin);
                       }
                   });

               } catch (JSONException e) {
                   e.printStackTrace();
               }

           }
       });
    }
}