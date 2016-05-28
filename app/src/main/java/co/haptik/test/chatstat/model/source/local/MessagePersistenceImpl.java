package co.haptik.test.chatstat.model.source.local;

import android.content.Context;

import java.util.Date;
import java.util.List;

import co.haptik.test.chatstat.model.Message;
import co.haptik.test.chatstat.model.MessageDao;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by Aditya Mehta on 28/05/16.
 */
public class MessagePersistenceImpl implements MessagePersistenceApi {

    private static final long CACHE_TIME = 1000*60*5;

    private MessageDao mMessageDao;

    public MessagePersistenceImpl(Context context) {
        mMessageDao = DatabaseHelper.getInstance(context).getDaoSession().getMessageDao();
    }

    @Override
    public Observable<List<Message>> getFreshMessages() {
        Observable<List<Message>> messageObs = Observable.create(new Observable.OnSubscribe<List<Message>>() {
            @Override
            public void call(Subscriber<? super List<Message>> subscriber) {
                List<Message> messages = mMessageDao.queryBuilder()
                        .where(MessageDao.Properties.TimeStamp.ge(new Date().getTime() - CACHE_TIME))
                        .orderAsc(MessageDao.Properties.Id)
                        .list();
                if(messages != null && messages.size() > 0) {
                    subscriber.onNext(messages);
                }
                subscriber.onCompleted();
            }
        });
        return messageObs;
    }

    @Override
    public Observable<List<Message>> getMessages() {
        Observable<List<Message>> messagesObs = Observable.create(new Observable.OnSubscribe<List<Message>>() {
            @Override
            public void call(Subscriber<? super List<Message>> subscriber) {
                List<Message> messages = mMessageDao.loadAll();
                subscriber.onNext(messages);
                subscriber.onCompleted();
            }
        });
        return messagesObs;
    }

    @Override
    public void saveMessages(final List<Message> messages) {
        for(Message message : messages) {
            message.setFavourite(false);
            Message storedMessage = mMessageDao.load(message.getId());
            if(storedMessage != null && storedMessage.getFavourite() != null) {
                message.setFavourite(storedMessage.getFavourite());
            }
        }
        mMessageDao.insertOrReplaceInTx(messages);
    }

    @Override
    public boolean updateMessage(Message message) {
        if(message != null) {
            long rowId = mMessageDao.insert(message);
            if(rowId > -1) {
                return true;
            }
        }
        return false;
    }
}
