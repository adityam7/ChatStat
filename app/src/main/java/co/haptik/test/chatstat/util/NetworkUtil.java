package co.haptik.test.chatstat.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Aditya Mehta on 28/05/16.
 */
public class NetworkUtil {
    private Context mContext;

    public NetworkUtil(Context context) {
        mContext = context;
    }

    public boolean isNetworkConnected() {
        if (mContext == null) {
            return true;
        }
        ConnectivityManager connectivityManager =
                (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo nwInfo = connectivityManager.getActiveNetworkInfo();
        if (nwInfo != null && nwInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }
}
