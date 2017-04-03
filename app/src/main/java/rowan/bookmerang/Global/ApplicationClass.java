package rowan.bookmerang.Global;

import android.app.Application;

import com.tsengvn.typekit.Typekit;

/**
 * Created by CHEONMYUNG on 2016-11-30.
 */

public class ApplicationClass extends Application {
    public void onCreate() {
        super.onCreate();
        Typekit.getInstance()
                .addNormal(Typekit.createFromAsset(this, "NotoSansKR-Regular-Hestia.otf"))
                .addBold(Typekit.createFromAsset(this, "NotoSansKR-Bold-Hestia.otf"));
    }
}
