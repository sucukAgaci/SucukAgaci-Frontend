package com.example.umurgogebakan.bilstop;

import android.content.Intent;
import android.graphics.DashPathEffect;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.umurgogebakan.bilstop.model.Response4Login;
import com.example.umurgogebakan.bilstop.request.ApiClient;
import com.example.umurgogebakan.bilstop.request.ConcreteCommand;
import com.example.umurgogebakan.bilstop.request.RetrofitCallInvoker;
import com.example.umurgogebakan.bilstop.request.RetrofitCallReceiver;
import com.example.umurgogebakan.bilstop.request.interfaced.ApiInterface;
import com.example.umurgogebakan.bilstop.request.interfaced.ICommandCallService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Dashboard extends AppCompatActivity {
    Button cars;
    TextView textView;

    final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
    RetrofitCallInvoker retrofitCallInvoker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        textView = (TextView) findViewById(R.id.textView);
        cars = (Button) findViewById(R.id.carsButton);
        retrofitCallInvoker = new RetrofitCallInvoker();
        RetrofitCallReceiver retrofitCallReceiver = new RetrofitCallReceiver();
        ICommandCallService commandCallService = new ConcreteCommand(retrofitCallReceiver);
        retrofitCallInvoker.setCommandCallService(commandCallService);

        cars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retrofitCallInvoker.callServiceAsync(setCall4showCars(Response4Login.class), new Callback<Response4Login>() {
                    @Override
                    public void onResponse(Call<Response4Login> call, Response<Response4Login> response) {


                        Toast.makeText(Dashboard.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        if(response.body().getError() == 0) {
                            textView.setText(response.body().getMessage());
                        }

                    }

                    @Override
                    public void onFailure(Call<Response4Login> call, Throwable t) {
                        Toast.makeText(Dashboard.this, "awfawfaw", Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });
    }

    public <T> Call<T> setCall4showCars(Class<T> tClass) {
        Call<T> call = (Call<T>) apiService.showCarsRequest("showCars");
        return call;
    }

}
