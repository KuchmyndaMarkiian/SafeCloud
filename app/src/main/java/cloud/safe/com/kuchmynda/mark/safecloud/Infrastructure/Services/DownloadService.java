package cloud.safe.com.kuchmynda.mark.safecloud.Infrastructure.Services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import cloud.safe.com.kuchmynda.mark.safecloud.Common.ApiConnection;
import cloud.safe.com.kuchmynda.mark.safecloud.Common.CommonData;
import cloud.safe.com.kuchmynda.mark.safecloud.Infrastructure.Services.SafeCloudServices.OkHttpManager;
import cloud.safe.com.kuchmynda.mark.safecloud.Infrastructure.UserAccount;
import cloud.safe.com.kuchmynda.mark.safecloud.Views.Fragments.StructureFragment;

/**
 * Created by Markiian Kuchmynda on 23.09.2017.
 */

public class DownloadService extends Service {
    private DownloadThread downloadThread;
    private Executor executor;

    @Override
    public void onCreate() {
        super.onCreate();

        downloadThread = new DownloadThread();
        executor = Executors.newSingleThreadExecutor();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        executor.execute(downloadThread);
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private class DownloadThread implements Runnable {

        @Override
        public void run() {
            OkHttpManager httpManager = new OkHttpManager(ApiConnection.ServerAddress);
            httpManager.putHeaders(UserAccount.getCurrentAccount().getApiAuthorization());
            Intent intent = new Intent(StructureFragment.broadcast_action);
            try {
                intent.putExtra(CommonData.PARAM_STATE, CommonData.STATUS_START);
                sendBroadcast(intent);

                httpManager.get(ApiConnection.FolderListAddress);
                int state = CommonData.STATUS_FINISH;
                if (httpManager.isSuccessful()) {
                    intent.putExtra(CommonData.FRAGMENT_MODEL_EXTRA, httpManager.getStringResult());
                    intent.putExtra(CommonData.FRAGMENT_CONDITION_EXTRA, true);
                } else {
                    state = CommonData.STATUS_ERROR;
                }
                intent.putExtra(CommonData.PARAM_STATE, state);
                sendBroadcast(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
            stopSelf();
        }
    }
}
