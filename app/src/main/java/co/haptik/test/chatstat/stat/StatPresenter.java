package co.haptik.test.chatstat.stat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import co.haptik.test.chatstat.model.Message;
import co.haptik.test.chatstat.model.Stat;
import co.haptik.test.chatstat.model.source.MessageDataManagerApi;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Aditya Mehta on 29/05/16.
 */
public class StatPresenter implements StatContract.Presenter {

    private StatContract.View mView;
    private MessageDataManagerApi mDataManager;

    public StatPresenter(StatContract.View view, MessageDataManagerApi dataManager) {
        mView = view;
        mDataManager = dataManager;
    }

    @Override
    public void loadStats(boolean forceRefresh) {
        mView.showProgress(true);
        if(!mDataManager.isNetworkAvailable()) {
            mView.showMessage("Network not connected");
        }
        mDataManager.loadMessages(forceRefresh)
                .map(new Func1<List<Message>, List<Stat>>() {
                    @Override
                    public List<Stat> call(List<Message> messages) {
                        HashMap<String, Stat> statMap = new HashMap<String, Stat>(messages.size());
                        for(Message message : messages) {
                            if (statMap.containsKey(message.getUsername())) {
                                Stat stat = statMap.get(message.getUsername());
                                stat.setMessagesPosted(stat.getMessagesPosted()+1);
                                if(message.getFavourite()) {
                                    stat.setMessagesFavourited(stat.getMessagesFavourited()+1);
                                }
                                statMap.put(message.getUsername(), stat);
                            } else {
                                Stat stat = new Stat();
                                stat.setMessagesPosted(1);
                                if(message.getFavourite()) {
                                    stat.setMessagesFavourited(1);
                                } else {
                                    stat.setMessagesFavourited(0);
                                }
                                stat.setImageUrl(message.getImageUrl());
                                stat.setName(message.getName());
                                stat.setUserName(message.getUsername());
                                statMap.put(message.getUsername(), stat);
                            }
                        }
                        return new ArrayList<Stat>(statMap.values());
                    }
                }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Stat>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showProgress(false);
                        mView.showError(e.getMessage(), true);
                    }

                    @Override
                    public void onNext(List<Stat> stats) {
                        mView.showProgress(false);
                        mView.showStats(stats);
                    }
                });
    }

    @Override
    public void cleanUp() {

    }
}
