package co.haptik.test.chatstat.util;

import co.haptik.test.chatstat.model.source.remote.MessageServiceResponse;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by Aditya Mehta on 28/05/16.
 */
public interface HaptikService {

    @GET("android/test_data/")
    Observable<MessageServiceResponse> getData();

}
