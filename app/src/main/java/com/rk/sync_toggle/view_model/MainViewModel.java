package com.rk.sync_toggle.view_model;

import android.app.Application;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.rk.sync_toggle.service.model.MessageDto;
import com.rk.sync_toggle.service.model.UserDto;
import com.rk.sync_toggle.service.repository.Repository;

import retrofit2.Response;

public class MainViewModel  extends AndroidViewModel {

    private Repository repository;
    private LiveData<UserDto> createUserLiveData;
    private LiveData<MessageDto> sendNotificationLiveData;
    String android_id;
    public MainViewModel(@NonNull Application application) {
        super(application);
        android_id = Settings.Secure.getString(application.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        repository = new Repository();
    }

    private void createUser(String fcmId) {

        UserDto dto = new UserDto(android_id,"SLAVE", fcmId);
        this.createUserLiveData = Repository.getInstance().createUser(dto);
    }

    public void sendNotification(String message) {

        this.sendNotificationLiveData = Repository.getInstance().sendNotification(message);
    }

    public LiveData<UserDto> getCreateUserLiveData(String fcmId) {
        createUser(fcmId);
        return createUserLiveData;
    }

    public LiveData<MessageDto> getNotificationLiveData(String message) {
        sendNotification(message);
        return sendNotificationLiveData;
    }
}
