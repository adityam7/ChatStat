package co.haptik.test.chatstat.util;

import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by Aditya Mehta on 29/05/16.
 */
public class ObservableScheduler {
    public static Observable schedule(Observable observable) {
        return observable.subscribeOn(Schedulers.immediate())
                .observeOn(Schedulers.immediate());
    }
}
