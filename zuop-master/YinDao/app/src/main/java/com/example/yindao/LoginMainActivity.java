package com.example.yindao;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;
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

public class LoginMainActivity extends AppCompatActivity {
    Button denglu;
    EditText zhanghao, mima;
    public static String token1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_main);
        zhanghao = findViewById(R.id.zhanghao);
        mima = findViewById(R.id.mima);
        denglu = findViewById(R.id.btn_denglu);
        denglu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkHttpClient client = new OkHttpClient().newBuilder()
                        .build();
                MediaType mediaType = MediaType.parse("application/json");
                RequestBody body = RequestBody.create(mediaType, "{\r\n    \"username\":\""+zhanghao.getText().toString()+"\",\r\n    \"password\":\""+mima.getText().toString()+"\"\r\n\r\n}");
                Request request = new Request.Builder()
                        .url("http://192.168.10.36/rest/account/login")
                        .method("POST", body)
                        .addHeader("Content-Type", "application/json")
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
                            String msg = jsonObject.getString("msg");
                            int code = jsonObject.getInt("code");

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(LoginMainActivity.this,msg,Toast.LENGTH_SHORT).show();
                                }
                            });
                            if (code == 200){
                                JSONObject data = jsonObject.getJSONObject("data");
                                String token = data.getString("token");
                                Intent intent = new Intent();
                                intent.setClass(LoginMainActivity.this,MainActivity.class);
                                startActivity(intent);
                                token1 = token;
                                finish();;

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
            }
        });
    }
}