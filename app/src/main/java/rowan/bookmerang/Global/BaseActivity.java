package rowan.bookmerang.Global;

import android.app.Activity;
import android.content.Context;

import com.tsengvn.typekit.TypekitContextWrapper;

// 폰트 적용을 위한 부모 클래스.
public class BaseActivity extends Activity {
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
   /* private static Typeface typeface;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        if(typeface == null) {
            typeface = Typeface.createFromAsset(this.getAssets(), "yayayaOB.otf");
        }
        setGlobalFont(getWindow().getDecorView());
    }

    private void setGlobalFont(View view) {
        if(view != null) {
            if(view instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup)view;
                int vgCnt = viewGroup.getChildCount();
                for(int i = 0; i<vgCnt; i++) {
                    View v = viewGroup.getChildAt(i);
                    if(v instanceof TextView) {
                        ((TextView) v).setTypeface(typeface);
                    }
                    setGlobalFont(v);
                }
            }
        }
    }*/
}
