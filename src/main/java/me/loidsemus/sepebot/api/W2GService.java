package me.loidsemus.sepebot.api;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface W2GService {
    @POST("rooms/create.json")
    Call<CreateRoomResponse> createRoomWithVideo(@Body RequestBody body);

    @POST("rooms/create.json")
    Call<CreateRoomResponse> createRoomWithoutVideo(@Body String w2g_api_key);

    public class RequestBody {
        String w2g_api_key;
        String share;

        public RequestBody(String w2g_api_key, String share) {
            this.w2g_api_key = w2g_api_key;
            this.share = share;
        }
    }
}
