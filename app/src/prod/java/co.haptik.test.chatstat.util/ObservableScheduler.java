package co.haptik.test.chatstat.util;

import java.util.List;

import co.haptik.test.chatstat.model.Message;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Aditya Mehta on 29/05/16.
 */
public class ObservableScheduler {
    public static Observable<List<Message>> schedule(Observable<List<Message>> observable) {
        return observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}