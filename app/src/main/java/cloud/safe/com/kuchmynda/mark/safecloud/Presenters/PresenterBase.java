package cloud.safe.com.kuchmynda.mark.safecloud.Presenters;

import android.content.ComponentCallbacks;

/**
 * Created by MARKAN on 15.09.2017.
 */

public abstract class PresenterBase<T extends ComponentCallbacks> {
    T view;

    public  void takeView(T view){
        this.view=view;
        execute();
    }

    protected abstract void execute();
    protected abstract void initModel();
    protected abstract void errorHandler();
}
