package com.example.umurgogebakan.bilstop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class DriverRegistry extends AppCompatActivity {
    final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
    RetrofitCallInvoker retrofitCallInvoker;


    Button next;
    EditText model;
    EditText color;
    EditText plates;
    EditText seats;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_registry);

        next = (Button) findViewById(R.id.carsButton);
        model = (EditText) findViewById(R.id.model);
        color = (EditText) findViewById(R.id.color);
        plates = (EditText) findViewById(R.id.plates);
        seats = (EditText) findViewById(R.id.seats);

        retrofitCallInvoker = new RetrofitCallInvoker();
        RetrofitCallReceiver retrofitCallReceiver = new RetrofitCallReceiver();
        ICommandCallService commandCallService = new ConcreteCommand(retrofitCallReceiver);
        retrofitCallInvoker.setCommandCallService(commandCallService);


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                retrofitCallInvoker.callServiceAsync(setCall4driverRegister(Response4Login.class), new Callback<Response4Login>() {
                    @Override
                    public void onResponse(Call<Response4Login> call, Response<Response4Login> response) {


                        Toast.makeText(DriverRegistry.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();


                    }

                    @Override
                    public void onFailure(Call<Response4Login> call, Throwable t) {
                        Toast.makeText(DriverRegistry.this, "awfawfaw", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
    public <T> Call<T> setCall4driverRegister(Class<T> tClass) {
        Call<T> call = (Call<T>) apiService.driverRegistryRequest("driverRegistry", true, model.getText().toString(),
                color.getText().toString(), plates.getText().toString(), Integer.valueOf(seats.getText().toString()));
        return call;
    }
}
