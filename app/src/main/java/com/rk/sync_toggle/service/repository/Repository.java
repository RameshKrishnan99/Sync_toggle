package com.rk.sync_toggle.service.repository;


import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.rk.sync_toggle.service.model.MessageDto;
import com.rk.sync_toggle.service.model.UserDto;
import com.rk.sync_toggle.service.repository.retrofit.APIInterface;
import com.rk.sync_toggle.service.repository.retrofit.RetroFit_Service;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {
    private final APIInterface apiInterface;
    static Repository repository;

    public Repository() {
        apiInterface = RetroFit_Service.getClient().create(APIInterface.class);
    }

    public LiveData<UserDto> createUser(final UserDto userDto) {
        final MutableLiveData<UserDto> data = new MutableLiveData<>();
        Call<UserDto> call = apiInterface.createUser(userDto.getDeviceId(), userDto.getType(), userDto.getFcmToken());
        call.enqueue(new Callback<UserDto>() {
            @Override
            public void onResponse(Call<UserDto> call, final Response<UserDto> response) {
                Log.e("Code", response.code() + "");
                if (response.body() != null)
                    data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<UserDto> call, Throwable t) {
                data.setValue(null);
                call.cancel();
            }
        });
        return data;
    }

    public LiveData<MessageDto> sendNotification(String message) {
        final MutableLiveData<MessageDto> data = new MutableLiveData<>();
        Call<MessageDto> call = apiInterface.sendNotification(message);
        call.enqueue(new Callback<MessageDto>() {
            @Override
            public void onResponse(Call<MessageDto> call, final Response<MessageDto> response) {
                Log.e("Code", response.code() + "");
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<MessageDto> call, Throwable t) {
                call.cancel();
            }
        });
        return data;
    }


    public static Repository getInstance() {
        if (repository == null)
            repository = new Repository();
        return repository;
    }



}
