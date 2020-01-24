package com.example.current;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.example.current.service.ApiService;
import com.example.current.service.Current;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity{
    private String maindata;
    private TextView textViewResult;
    private TextView currentDateTimeStringView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewResult = findViewById(R.id.text_view_result);
        currentDateTimeStringView = findViewById(R.id.currentDateTimeString);
        current_view();
        time_view();

    }
    public void time_view(){
        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
        currentDateTimeStringView.setText(currentDateTimeString);
        refresh(1000);
    }

    public void current_view(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.nexpie.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService service = retrofit.create(ApiService.class);

        Call<Current> call = service.getCurrent();


        call.enqueue(new Callback<Current>() {
            @Override
            public void onResponse(Call<Current> call, Response<Current> response) {
                if(!response.isSuccessful()){
                    textViewResult.setText("Code: "+response.code());
                    return;
                }
                Current current = response.body();
                Object data = current.getData();
                String Irms = data.toString().replace("{Irms=","");

                textViewResult.setText(Irms.substring(0,4).replace(",",""));

            }

            @Override
            public void onFailure(Call<Current> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });

    }

    public void refresh(int milliseconds){
        final Handler handler = new Handler();

        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                current_view();
                time_view();
            }
        };

        handler.postDelayed(runnable,milliseconds);
    }
}
