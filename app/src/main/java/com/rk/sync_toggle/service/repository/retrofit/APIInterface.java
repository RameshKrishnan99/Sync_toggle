package com.rk.sync_toggle.service.repository.retrofit;



import com.rk.sync_toggle.service.model.MessageDto;
import com.rk.sync_toggle.service.model.UserDto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by user1 on 8/5/19.
 */

public interface APIInterface {
    @POST("/update_slave_details.php")
    @FormUrlEncoded
    Call<UserDto> createUser(@Field("device_id") String deviceId,
                             @Field("type") String type, @Field("fcm_token") String fcmId);

    @POST("/send_notification_to_slave.php")
    @FormUrlEncoded
    Call<MessageDto> sendNotification(@Field("message") String message);
    //    @POST("/get_location.php")
//    Call<LocationDto> doGetLocation(@Body LocationDto location);
//    @Headers("Content-Type: application/x-www-form-urlencoded")
    /*@FormUrlEncoded
    @POST("/get_location.php")
    Call<Location_Request> doGetLocation(@FieldMap HashMap<String, String> data);
*/
   /* @POST("/login/validateuser")
    Call<Location_Request> doGetLogin(@Body LoginDto dto);



    @GET("/api/users?")
    Call<UserList> doGetUserList(@Query("page") String page);

    @FormUrlEncoded
    @POST("/api/users?")
    Call<UserList> doCreateUserWithField(@Field("name") String name, @Field("job") String job);*/
}
