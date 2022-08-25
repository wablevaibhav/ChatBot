package in.icomputercoding.chatbot;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import in.icomputercoding.chatbot.Model.Chats;
import in.icomputercoding.chatbot.databinding.BotMsgRvItemBinding;
import in.icomputercoding.chatbot.databinding.UserMsgRvItemBinding;

public class ChatRVAdapter extends RecyclerView.Adapter {

    private final ArrayList<Chats> chatsArrayList;

    public ChatRVAdapter(ArrayList<Chats> chatsArrayList) {
        this.chatsArrayList = chatsArrayList;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == 0) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_msg_rv_item, parent, false);
            return new UserViewHolder(view);
        } else if (viewType == 1) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bot_msg_rv_item, parent, false);
            return new BotViewHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Chats chats = chatsArrayList.get(position);
        switch (chats.getSender()) {

            case "user":
                ((UserViewHolder) holder).binding.TvUser.setText(chats.getMessage());
                break;
            case "bot":
                ((BotViewHolder) holder).binding.TvBot.setText(chats.getMessage());
                break;

        }


    }

    @Override
    public int getItemViewType(int position) {
        switch (chatsArrayList.get(position).getSender()) {
            case "user":
                return 0;
            case "bot":
                return 1;
            default:
                return -1;
        }
    }

    @Override
    public int getItemCount() {
        return chatsArrayList.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        UserMsgRvItemBinding binding;

        public UserViewHolder(@NonNull View view) {
            super(view);
            binding = UserMsgRvItemBinding.bind(view);
        }
    }

    public static class BotViewHolder extends RecyclerView.ViewHolder {
        BotMsgRvItemBinding binding;

        public BotViewHolder(@NonNull View view) {
            super(view);
            binding = BotMsgRvItemBinding.bind(view);
        }
    }

}
