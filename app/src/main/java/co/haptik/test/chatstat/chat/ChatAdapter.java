package co.haptik.test.chatstat.chat;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import co.haptik.test.chatstat.R;
import co.haptik.test.chatstat.model.Message;

/**
 * Created by Aditya Mehta on 29/05/16.
 */
public class ChatAdapter extends RecyclerView.Adapter<ChatHolder> {

    private List<Message> mMessages;
    private ChatInteractionListener mInteractionListener;

    public ChatAdapter(List<Message> messages, ChatInteractionListener listener) {
        mMessages = messages;
        mInteractionListener = listener;
    }

    @Override
    public ChatHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_chat, parent, false);
        ChatHolder holder = new ChatHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ChatHolder holder, int position) {
        holder.bind(mMessages.get(position), mInteractionListener);
    }

    @Override
    public int getItemCount() {
        return mMessages.size();
    }
}
