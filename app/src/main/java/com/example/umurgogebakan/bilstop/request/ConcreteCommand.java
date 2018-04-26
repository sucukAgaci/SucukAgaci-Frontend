package com.example.umurgogebakan.bilstop.request;

import android.app.Activity;


import com.example.umurgogebakan.bilstop.request.interfaced.ICommandCallService;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by fkocak on 4.01.2018.
 */

public class ConcreteCommand implements ICommandCallService {

    RetrofitCallReceiver retrofitCallReceiver;

    public ConcreteCommand(RetrofitCallReceiver retrofitCallReceiver){
        this.retrofitCallReceiver = retrofitCallReceiver;
    }


    @Override
    public <T> void callAsync(Call<T> tCall, Callback<T> callback) {

        retrofitCallReceiver.callServiceAsync(tCall,callback);

    }

}
