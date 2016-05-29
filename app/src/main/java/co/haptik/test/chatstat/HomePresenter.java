package co.haptik.test.chatstat;

import java.util.List;

import co.haptik.test.chatstat.model.Message;
import co.haptik.test.chatstat.model.source.MessageDataManagerApi;
import co.haptik.test.chatstat.util.ObservableScheduler;
import rx.Subscriber;
import rx.Subscription;

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
        mMessagesSubscription = ObservableScheduler.schedule(mDataManager.loadMessages(forceRefresh))
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
