package cloud.safe.com.kuchmynda.mark.safecloud.Presenters;

import android.app.Activity;
import android.app.Fragment;
import android.content.ComponentCallbacks;
import android.widget.Toast;

/**
 * Created by Markiian Kuchmynda on 15.09.2017.
 */

public abstract class PresenterBase<T extends ComponentCallbacks> {
    T view;

    public  void takeView(T view){
        this.view=view;
    }

    protected abstract void initModel();
    public void messageHandler(final String message)
    {
        final Activity activity;
        if(view instanceof Fragment)
        {
            activity=((Fragment)view).getActivity();
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
                }
            });
        }
        else if(view instanceof Activity)
        {
            activity=(Activity) view;
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
