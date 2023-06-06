package com.example.yindao.zhuye;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yindao.FangwuShu;
import com.example.yindao.LoginMainActivity;
import com.example.yindao.R;
import com.example.yindao.YinDao;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Wode#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Wode extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Wode() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Wode.
     */
    // TODO: Rename and change types and number of parameters
    public static Wode newInstance(String param1, String param2) {
        Wode fragment = new Wode();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        TextView tv_shu,tv_name,tv_qianming;
        ImageView iv;
        View inflate = inflater.inflate(R.layout.fragment_wode, container, false);
        tv_shu = inflate.findViewById(R.id.tv_fangwushu);
        tv_shu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), FangwuShu.class);
                startActivity(intent);

            }
        });
        iv = inflate.findViewById(R.id.iv);
        tv_name = inflate.findViewById(R.id.tv_name);
        tv_qianming = inflate.findViewById(R.id.tv_qianming);
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
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tv_shu.setText("房屋数量"+data.length());
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });

        Request request1 = new Request.Builder()
                .url("http://192.168.10.36/rest/account/accountInfo")
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("authorization", LoginMainActivity.token1)
                .build();
        client.newCall(request1).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String string = response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(string);
                    JSONObject data = jsonObject.getJSONObject("data");
                    String nick_name = data.getString("nick_name");
                    String signature = data.getString("signature");
                    URL url = new URL("http://192.168.10.36/"+data.getString("avatar"));
                    Bitmap bitmap = BitmapFactory.decodeStream(url.openStream());
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tv_name.setText(nick_name);
                            tv_qianming.setText(signature);
                            iv.setImageBitmap(bitmap);

                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
        // Inflate the layout for this fragment
        return inflate;
    }
}