package co.haptik.test.chatstat;

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
public class HomePresenter implements HomeContract.Presenter {

    private static final String TAG = HomePresenter.class.getSimpleName();
    private HomeContract.View mView;
    private MessageDataManagerApi mDataManager;
    private Subscription mMessagesSubscription;

    public HomePresenter(HomeContract.View view, MessageDataManagerApi dataManager) {
        mView = view;
        mDataManager = dataManager;
    }

    @Override
    public void loadContent(boolean forceRefresh) {
        mView.showProgress(true);
        if(!mDataManager.isNetworkAvailable()) {
            mView.showMessage("Network not connected");
        }
        mMessagesSubscription = mDataManager.loadMessages(forceRefresh)
                .subscribeOn(Schedulers.io())
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
                        if(messages != null && messages.size() > 0) {
                            mView.showProgress(false);
                            mView.showContent();
                        } else {
                            mView.showProgress(false);
                            mView.showError("No Messages Available", true);
                        }
                    }
                });

    }

    @Override
    public void cleanUp() {
        if(mMessagesSubscription != null && !mMessagesSubscription.isUnsubscribed()) {
            mMessagesSubscription.unsubscribe();
        }
    }
}
