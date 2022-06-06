package in.icomputercoding.chatbot;

import in.icomputercoding.chatbot.Model.Message;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface RetrofitAPI {

    @GET
    Call<Message> getMessage(@Url String url);

}
