package com.example.umurgogebakan.bilstop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;
import android.text.method.PasswordTransformationMethod;

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

public class MainActivity extends AppCompatActivity {
    // simdilik her classta eklenecektir.
    final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
    RetrofitCallInvoker retrofitCallInvoker;



    EditText userName;
    EditText password;

    Button signIn;
    Button signUp;

    Switch switchBtn;

    String realUserName4Surucu = "umur.fatih";
    String realPassword4Surucu = "123";

    String realUserName4Otostop = "fatih.umur.gokhan";
    String realPassword4Otostop = "1234";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userName = (EditText) findViewById(R.id.userName);
        password = (EditText) findViewById(R.id.seats);
        signIn = (Button) findViewById(R.id.signIn);
        signUp = (Button) findViewById(R.id.signUpButton);
        switchBtn = (Switch) findViewById(R.id.switch1);


        // simdilik her classta eklenecektir.
        retrofitCallInvoker = new RetrofitCallInvoker();
        RetrofitCallReceiver retrofitCallReceiver = new RetrofitCallReceiver();
        ICommandCallService commandCallService = new ConcreteCommand(retrofitCallReceiver);
        retrofitCallInvoker.setCommandCallService(commandCallService);


        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                retrofitCallInvoker.callServiceAsync(setCall4Login(Response4Login.class), new Callback<Response4Login>() {
                    @Override
                    public void onResponse(Call<Response4Login> call, Response<Response4Login> response) {


                        Toast.makeText(MainActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        if (switchBtn.isChecked() && response.body().getError() == 0) {
                            startActivity(new Intent(getApplicationContext(), Dashboard.class));
                        } else if (response.body().getError() == 0) {
                            startActivity(new Intent(getApplicationContext(), OtostopEkrani.class));
                        }

                    }

                    @Override
                    public void onFailure(Call<Response4Login> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "awfawfaw", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });




        switchBtn.setChecked(false);
        switchBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isCheck) {
                if (isCheck) {
                    Toast.makeText(MainActivity.this, "Surucu", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Otostopcu", Toast.LENGTH_SHORT).show();
                }

                }
             });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SignUp.class));
            }
        });

       /* signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strUserName = userName.getText().toString();
                String strPassword = password.getText().toString();

                if (switchBtn.isChecked()) {
                    if (strUserName.equals(realUserName4Surucu) && strPassword.equals(realPassword4Surucu)) {
                        startActivity(new Intent(getApplicationContext(), Dashboard.class));
                    } else {
                        Toast.makeText(MainActivity.this, "Giriş Bilgileri yanlıştır", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (strUserName.equals(realUserName4Otostop) && strPassword.equals(realPassword4Otostop)) {
                        startActivity(new Intent(getApplicationContext(), OtostopEkrani.class));
                    } else {
                        Toast.makeText(MainActivity.this, "Giriş Bilgileri yanlıştır", Toast.LENGTH_SHORT).show();
                    }


                }


            }
        }); */





        password.setTransformationMethod(new AsteriskPasswordTransformationMethod () );
    }
    public class AsteriskPasswordTransformationMethod extends PasswordTransformationMethod {

        @Override
        public CharSequence getTransformation(CharSequence source, View view) {
            return new PasswordCharSequence(source);
        }

        private class PasswordCharSequence implements CharSequence {
            private CharSequence mSource;
            public PasswordCharSequence(CharSequence source) {
                mSource = source; // Store char sequence
            }
            public char charAt(int index) {
                return '*'; // This is the important part
            }
            public int length() {
                return mSource.length(); // Return default
            }
            public CharSequence subSequence(int start, int end) {
                return mSource.subSequence(start, end); // Return default
            }
        }
    }


    public <T> Call<T> setCall4Login(Class<T> tClass) {
        Call<T> call = (Call<T>) apiService.loginRequest("login",userName.getText().toString(),password.getText().toString());
        return call;
    }
}


