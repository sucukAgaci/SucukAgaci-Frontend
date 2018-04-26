package com.example.umurgogebakan.bilstop.request.interfaced;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by fkocak on 5.01.2018.
 */

public interface ICommandCallService  {
    public <T> void callAsync(Call<T> tCall, Callback<T> callback);
}