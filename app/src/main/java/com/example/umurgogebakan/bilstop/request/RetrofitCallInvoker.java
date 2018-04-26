package com.example.umurgogebakan.bilstop.request;

import com.example.umurgogebakan.bilstop.request.interfaced.ICommandCallService;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by fkocak on 4.01.2018.
 */

public class RetrofitCallInvoker {
    private ICommandCallService commandCallService;

    public void setCommandCallService(ICommandCallService commandCallService){
        this.commandCallService = commandCallService;
    }


    public <T> void callServiceAsync(Call<T> tCall, Callback<T> callback){

        commandCallService.callAsync(tCall,callback);

    }
}