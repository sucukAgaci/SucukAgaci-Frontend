package com.example.umurgogebakan.bilstop.request;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by fkocak on 5.01.2018.
 */

public class RetrofitCallReceiver {

    public <T> void callServiceAsync(Call<T> tCall, Callback<T> callback){
        tCall.enqueue( callback );
    }

}