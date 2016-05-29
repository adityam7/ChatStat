package co.haptik.test.chatstat;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import co.haptik.test.chatstat.model.Message;
import co.haptik.test.chatstat.model.source.MessageDataManagerApi;
import rx.Observable;
import rx.observers.TestSubscriber;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Aditya Mehta on 29/05/16.
 */
public class HomePresenterTest {

    private static final int NO_OF_MESSAGES = 5;
    private static List<Message> MESSAGES;
    private static List<Message> EMPTY_MESSAGES = new ArrayList<>(0);

    private void setUpMessages() {
        MESSAGES = new ArrayList<>(NO_OF_MESSAGES);
        for(int i=0; i<NO_OF_MESSAGES; i++) {
            Date currentDate = new Date();
            Message message = new Message(currentDate.getTime());
            MESSAGES.add(message);
        }
    }

    @Mock
    private HomeContract.View mView;

    @Mock
    private MessageDataManagerApi mDataManager;

    private TestSubscriber<List<Message>> mSubscriber;

    private HomePresenter mHomePresenter;

    @Before
    public void setUpHomePresenter() {

        MockitoAnnotations.initMocks(this);
        mSubscriber = new TestSubscriber<>();
        mHomePresenter = new HomePresenter(mView, mDataManager);
    }

    @Test
    public void loadMessagesFromDataManagerIntoView() {
        setUpMessages();
        when(mDataManager.isNetworkAvailable()).thenReturn(true);
        Observable<List<Message>> testObs = Observable.just(MESSAGES);
        when(mDataManager.loadMessages(false)).thenReturn(testObs);

        mHomePresenter.loadContent(false);
        verify(mView).showProgress(true);
        verify(mDataManager).isNetworkAvailable();
        verify(mDataManager).loadMessages(false);

        testObs.subscribe(mSubscriber);
        mSubscriber.assertNoErrors();
        mSubscriber.assertReceivedOnNext(Arrays.asList(MESSAGES));

        verify(mView).showProgress(false);
        verify(mView).showContent();
    }

    @Test
    public void loadMessagesFromDataManagerIntoViewNoInternet() {
        setUpMessages();
        when(mDataManager.isNetworkAvailable()).thenReturn(false);
        Observable<List<Message>> testObs = Observable.just(MESSAGES);
        when(mDataManager.loadMessages(false)).thenReturn(testObs);

        mHomePresenter.loadContent(false);
        verify(mView).showProgress(true);
        verify(mDataManager).isNetworkAvailable();
        verify(mView).showMessage("Network not connected");
        verify(mDataManager).loadMessages(false);

        testObs.subscribe(mSubscriber);
        mSubscriber.assertNoErrors();
        mSubscriber.assertReceivedOnNext(Arrays.asList(MESSAGES));

        verify(mView).showProgress(false);
        verify(mView).showContent();
    }

    @Test
    public void handelDataManagerError() {
        when(mDataManager.isNetworkAvailable()).thenReturn(true);
        Exception exception = new Exception("Sample Exception");
        Observable<List<Message>> testObs = Observable.error(exception);
        when(mDataManager.loadMessages(false)).thenReturn(testObs);

        mHomePresenter.loadContent(false);
        verify(mView).showProgress(true);
        verify(mDataManager).isNetworkAvailable();
        verify(mDataManager).loadMessages(false);

        testObs.subscribe(mSubscriber);
        mSubscriber.assertError(exception);

        verify(mView).showProgress(false);
        verify(mView).showError(exception.getMessage(), true);
    }

    @Test
    public void loadEmptyMessagesIntoView() {
        when(mDataManager.isNetworkAvailable()).thenReturn(true);
        Observable<List<Message>> testObs = Observable.just(EMPTY_MESSAGES);
        when(mDataManager.loadMessages(false)).thenReturn(testObs);

        mHomePresenter.loadContent(false);
        verify(mView).showProgress(true);
        verify(mDataManager).isNetworkAvailable();
        verify(mDataManager).loadMessages(false);

        testObs.subscribe(mSubscriber);
        mSubscriber.assertNoErrors();
        mSubscriber.assertReceivedOnNext(Arrays.asList(EMPTY_MESSAGES));

        verify(mView).showProgress(false);
        verify(mView).showError("No Messages Available", true);
    }

}
