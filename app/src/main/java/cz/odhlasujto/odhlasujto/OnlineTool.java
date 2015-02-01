package cz.odhlasujto.odhlasujto;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class OnlineTool {
    private static Context context;
    public static void setContext(Context c){
        context = c;
    }

    public static boolean isOnline(){
    ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info == null) {
            return false;
        }
        else return info.isConnected();
    }
}
