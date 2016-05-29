package co.haptik.test.chatstat.stat;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import co.haptik.test.chatstat.R;
import co.haptik.test.chatstat.chat.ChatFragment;
import co.haptik.test.chatstat.model.Stat;
import co.haptik.test.chatstat.model.source.MessageDataManager;
import co.haptik.test.chatstat.model.source.MessageDataManagerApi;
import co.haptik.test.chatstat.model.source.local.MessagePersistenceImpl;
import co.haptik.test.chatstat.model.source.remote.MessageServiceImpl;

public class StatFragment extends Fragment implements StatContract.View {

    @BindView(R.id.recyclerview)
    protected RecyclerView mRecyclerView;

    @BindView(R.id.progressbar)
    protected ProgressBar mProgressBar;

    @BindView(R.id.error_layout)
    protected RelativeLayout mErrorLayout;

    @BindView(R.id.errorText)
    protected TextView mErrorText;

    @BindView(R.id.retry)
    protected Button mRetry;

    private StatContract.Presenter mPresenter;

    public StatFragment() {
        // Required empty public constructor
    }

    public static StatFragment newInstance() {
        StatFragment fragment = new StatFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MessageDataManagerApi dataManager = new MessageDataManager(getContext(),
                new MessageServiceImpl(),
                new MessagePersistenceImpl(getContext()));
        mPresenter = new StatPresenter(this, dataManager);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        ButterKnife.bind(this, view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter(ChatFragment.MESSAGE_CHANGED);
        getActivity().registerReceiver(mReceiver, intentFilter);
        mPresenter.loadStats(false);
    }

    @Override
    public void onStop() {
        super.onStop();
        getActivity().unregisterReceiver(mReceiver);
        mPresenter.cleanUp();
    }

    @Override
    public void showStats(List<Stat> stats) {
        StatAdapter adapter = new StatAdapter(stats);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void showProgress(boolean show) {
        if(show) {
            mRecyclerView.setVisibility(View.GONE);
            mProgressBar.setVisibility(View.VISIBLE);
        } else {
            mRecyclerView.setVisibility(View.VISIBLE);
            mProgressBar.setVisibility(View.GONE);
        }
        mErrorLayout.setVisibility(View.GONE);
    }

    @Override
    public void showMessage(String message) {
        Snackbar.make(mRecyclerView, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showError(String message, boolean showRetry) {
        mRecyclerView.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.GONE);
        mErrorLayout.setVisibility(View.VISIBLE);
        mErrorText.setText(message);
        mRetry.setVisibility(showRetry ? View.VISIBLE : View.GONE);
    }

    @OnClick(R.id.retry)
    void retryClicked() {
        mPresenter.loadStats(true);
    }

    BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            mPresenter.loadStats(false);
        }
    };
}
