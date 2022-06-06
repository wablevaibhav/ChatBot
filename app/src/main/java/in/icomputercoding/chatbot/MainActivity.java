package in.icomputercoding.chatbot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Objects;

import in.icomputercoding.chatbot.Model.Chats;
import in.icomputercoding.chatbot.Model.Message;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    RecyclerView RVChats;
    EditText EdtMsg;
    FloatingActionButton Send;
    private ArrayList<Chats> chats;
    ChatRVAdapter chatRVAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RVChats = findViewById(R.id.RVChats);
        EdtMsg = findViewById(R.id.EdtMsg);
        Send = findViewById(R.id.Send);

        chats = new ArrayList<>();
        chatRVAdapter = new ChatRVAdapter(chats);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        RVChats.setLayoutManager(layoutManager);
        RVChats.setAdapter(chatRVAdapter);

        Send.setOnClickListener(v -> {
            if (EdtMsg.getText().toString().isEmpty()) {
                Toast.makeText(MainActivity.this, "Please enter your message.", Toast.LENGTH_SHORT).show();
                return;
            }
            getResponse(EdtMsg.getText().toString());
            EdtMsg.setText("");
        });


    }

    @SuppressLint("NotifyDataSetChanged")
    private void getResponse(String msg) {

        String USER_KEY = "user";
        String BOT_KEY = "bot";
        chats.add(new Chats(msg, USER_KEY));
        chatRVAdapter.notifyDataSetChanged();
        String url = "http://api.brainshop.ai/get?bid=167026&key=sLAqnpkO2kFKUcAf&uid=[uid]&msg="+msg;
        String BASE_URL = "http://api.brainshop.ai/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        Call<Message> messageCall = retrofitAPI.getMessage(url);
        messageCall.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(@NonNull Call<Message> call, @NonNull Response<Message> response) {
                if (response.isSuccessful()) {
                    Message message = response.body();
                    chats.add(new Chats(Objects.requireNonNull(message).getCnt(), BOT_KEY));
                    chatRVAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Message> call, @NonNull Throwable t) {
                chats.add(new Chats(t.getMessage(), BOT_KEY));
                chatRVAdapter.notifyDataSetChanged();

            }
        });

    }
}