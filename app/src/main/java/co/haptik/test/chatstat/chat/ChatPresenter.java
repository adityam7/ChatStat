package co.haptik.test.chatstat.chat;

import java.util.List;

import co.haptik.test.chatstat.model.Message;
import co.haptik.test.chatstat.model.source.MessageDataManagerApi;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Aditya Mehta on 28/05/16.
 */
public class ChatPresenter implements ChatContract.Presenter {

    private ChatContract.View mView;
    private MessageDataManagerApi mDataManager;
    private Subscription mMessagesSubscription;

    public ChatPresenter(ChatContract.View view, MessageDataManagerApi dataManager) {
        mView = view;
        mDataManager = dataManager;
    }

    @Override
    public void loadMessages(boolean forceRefresh) {
        mView.showProgress(true);
        if(!mDataManager.isNetworkAvailable()) {
            mView.showMessage("Network not connected");
        }
        mMessagesSubscription = mDataManager.loadMessages(forceRefresh)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Message>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showProgress(false);
                        mView.showError(e.getMessage(), true);
                    }

                    @Override
                    public void onNext(List<Message> messages) {
                        mView.showProgress(false);
                        mView.showMessages(messages);
                    }
                });
    }

    @Override
    public void messageFavouriteToggle(Message message) {
        message.setFavourite(!message.getFavourite());
        mDataManager.updateMessage(message);
        mView.broadcastMessageFavourited();
    }

    @Override
    public void cleanUp() {
        if(mMessagesSubscription != null && !mMessagesSubscription.isUnsubscribed()) {
            mMessagesSubscription.unsubscribe();
        }
    }
}
