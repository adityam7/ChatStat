package co.haptik.test.chatstat.chat;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.haptik.test.chatstat.R;
import co.haptik.test.chatstat.model.Message;

/**
 * Created by Aditya Mehta on 29/05/16.
 */
public class ChatHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.profilepic)
    protected ImageView mProfilePic;

    @BindView(R.id.favourite)
    protected ImageView mFavorite;

    @BindView(R.id.username)
    protected TextView mUserName;

    @BindView(R.id.name)
    protected TextView mName;

    @BindView(R.id.body)
    protected TextView mBody;

    @BindView(R.id.message_time)
    protected TextView mMessageTime;

    public ChatHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(final Message message, final ChatInteractionListener listener) {
        mUserName.setText(message.getUsername());
        mName.setText(message.getName());
        mBody.setText(message.getBody());
        mMessageTime.setText(message.getFormattedDateTime());
        if(message.getFavourite()) {
            mFavorite.setImageResource(R.drawable.ic_favorite);
        } else {
            mFavorite.setImageResource(R.drawable.ic_favorite_border);
        }
        Glide.with(mProfilePic.getContext())
                .load(message.getImageUrl())
                .placeholder(R.drawable.ic_face)
                .crossFade()
                .into(mProfilePic);

        mFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!message.getFavourite()) {
                    mFavorite.setImageResource(R.drawable.ic_favorite);
                } else {
                    mFavorite.setImageResource(R.drawable.ic_favorite_border);
                }
                listener.messageFavourited(message);

            }
        });

    }

}
