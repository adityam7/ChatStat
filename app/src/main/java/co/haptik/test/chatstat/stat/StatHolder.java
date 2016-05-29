package co.haptik.test.chatstat.stat;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.haptik.test.chatstat.R;
import co.haptik.test.chatstat.model.Stat;

/**
 * Created by Aditya Mehta on 29/05/16.
 */
public class StatHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.profilepic)
    protected ImageView mProfilePic;

    @BindView(R.id.favourite_number)
    protected TextView mFavorites;

    @BindView(R.id.post_number)
    protected  TextView mPosts;

    @BindView(R.id.username)
    protected TextView mUserName;

    @BindView(R.id.name)
    protected TextView mName;

    public StatHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(Stat stat) {
        mFavorites.setText(String.valueOf(stat.getMessagesFavourited()));
        mPosts.setText(String.valueOf(stat.getMessagesPosted()));
        mName.setText(stat.getName());
        mUserName.setText(stat.getUserName());

        Glide.with(mProfilePic.getContext())
                .load(stat.getImageUrl())
                .placeholder(R.drawable.ic_face)
                .crossFade()
                .into(mProfilePic);
    }

}
