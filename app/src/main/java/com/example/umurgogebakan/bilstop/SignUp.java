package com.example.umurgogebakan.bilstop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
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

public class SignUp extends AppCompatActivity {
    Button next;
    EditText name;
    EditText surname;
    EditText password;
    EditText repassword;
    EditText email;
    EditText age;

    final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
    RetrofitCallInvoker retrofitCallInvoker;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up2);




        next = (Button) findViewById(R.id.carsButton);
        name = (EditText) findViewById(R.id.model);
        surname = (EditText) findViewById(R.id.color);
        password = (EditText) findViewById(R.id.seats);
        repassword = (EditText) findViewById(R.id.repassword);
        email = (EditText) findViewById(R.id.plates);
        age = (EditText) findViewById(R.id.age);


        retrofitCallInvoker = new RetrofitCallInvoker();
        RetrofitCallReceiver retrofitCallReceiver = new RetrofitCallReceiver();
        ICommandCallService commandCallService = new ConcreteCommand(retrofitCallReceiver);
        retrofitCallInvoker.setCommandCallService(commandCallService);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retrofitCallInvoker.callServiceAsync(setCall4signUp(Response4Login.class), new Callback<Response4Login>() {
                    @Override
                    public void onResponse(Call<Response4Login> call, Response<Response4Login> response) {


                        Toast.makeText(SignUp.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        if(response.body().getError() == 0) {
                            startActivity(new Intent(getApplicationContext(), Verification.class));
                        }

                    }

                    @Override
                    public void onFailure(Call<Response4Login> call, Throwable t) {
                        Toast.makeText(SignUp.this, "awfawfaw", Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });
    }

    public <T> Call<T> setCall4signUp(Class<T> tClass) {
        Call<T> call = (Call<T>) apiService.signUpRequest("signUp", name.getText().toString(), surname.getText().toString(),
                password.getText().toString(), email.getText().toString(), Integer.valueOf(age.getText().toString()));
        return call;
    }
}
