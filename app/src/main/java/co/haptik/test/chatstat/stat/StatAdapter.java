package co.haptik.test.chatstat.stat;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import co.haptik.test.chatstat.R;
import co.haptik.test.chatstat.model.Stat;

/**
 * Created by Aditya Mehta on 29/05/16.
 */
public class StatAdapter extends RecyclerView.Adapter<StatHolder> {

    private List<Stat> mStats;

    public StatAdapter(List<Stat> stats) {
        mStats = stats;
    }

    @Override
    public StatHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_stat, parent, false);
        return new StatHolder(view);
    }

    @Override
    public void onBindViewHolder(StatHolder holder, int position) {
        holder.bind(mStats.get(position));
    }

    @Override
    public int getItemCount() {
        return mStats.size();
    }
}
