package com.example.umurgogebakan.bilstop;

import android.content.Intent;
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

public class Verification extends AppCompatActivity {
    final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
    RetrofitCallInvoker retrofitCallInvoker;

    Button verify;
    EditText verifCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        verify = (Button) findViewById(R.id.verify);
        verifCode = (EditText) findViewById(R.id.verifCode);

        retrofitCallInvoker = new RetrofitCallInvoker();
        RetrofitCallReceiver retrofitCallReceiver = new RetrofitCallReceiver();
        ICommandCallService commandCallService = new ConcreteCommand(retrofitCallReceiver);
        retrofitCallInvoker.setCommandCallService(commandCallService);


        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                retrofitCallInvoker.callServiceAsync(setCall4Verification(Response4Login.class), new Callback<Response4Login>() {
                    @Override
                    public void onResponse(Call<Response4Login> call, Response<Response4Login> response) {
                        Toast.makeText(Verification.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        if(response.body().getError() == 0) {
                            startActivity(new Intent(getApplicationContext(), DriverRegistry.class));
                        }

                    }

                    @Override
                    public void onFailure(Call<Response4Login> call, Throwable t) {
                        Toast.makeText(Verification.this, "awfawfaw", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    public <T> Call<T> setCall4Verification(Class<T> tClass) {
        Call<T> call = (Call<T>) apiService.verifRequest("verification", verifCode.getText().toString());
        return call;
    }
}
