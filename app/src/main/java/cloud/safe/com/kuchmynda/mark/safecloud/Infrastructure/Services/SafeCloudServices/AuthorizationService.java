package cloud.safe.com.kuchmynda.mark.safecloud.Infrastructure.Services.SafeCloudServices;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import cloud.safe.com.kuchmynda.mark.safecloud.Common.ApiConnection;
import cloud.safe.com.kuchmynda.mark.safecloud.Common.CustomTypes.Tuple;
import cloud.safe.com.kuchmynda.mark.safecloud.Models.Authorization.LoginModel;
import cloud.safe.com.kuchmynda.mark.safecloud.Models.Authorization.RegisterModel;
import cloud.safe.com.kuchmynda.mark.safecloud.Models.ErrorModel;
import okhttp3.OkHttpClient;
import okhttp3.Response;

/**
 * Created by Markiian Kuchmynda on 15.09.2017.
 */

public class AuthorizationService {
    private final String hostUrl;
    private final ErrorModel errorModel;
    private Response response;


    public AuthorizationService(String hostUrl) {
        errorModel = new ErrorModel();
        this.hostUrl = hostUrl;
    }
    public boolean signIn(String url,LoginModel loginModel) {
        OkHttpManager manager = new OkHttpManager(hostUrl);
        manager.putHeaders(new Tuple<>("Content-Type", ApiConnection.MimeFormUnlencoded),
                new Tuple<>("Accept", ApiConnection.MimeJson));
        manager.putBody(new Tuple<>("grant_type", "password"),
                new Tuple<>("username", loginModel.getEmail()),
                new Tuple<>("password", loginModel.getPassword()));
        manager.post(url);
        response=manager.getResponse();
        if (manager.isSuccessful()) {
            return true;
        } else {
            errorModel.errorDescription = manager.getMessage();
        }
        return false;
    }

    public boolean signUp(String url, RegisterModel registerModel) {
                OkHttpManager manager = new OkHttpManager(hostUrl);
        manager.putHeaders(new Tuple<>("Content-Type", ApiConnection.MimeFormUnlencoded));
        manager.putBody(registerModel);
        manager.post(url);
        response=manager.getResponse();
        if (manager.isSuccessful()) {
            return true;
        } else {
            errorModel.errorDescription = manager.getMessage();
        }
        return false;
    }

    public String  getLastResponseBody() {
        try {
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public String getError(){
        return errorModel.errorDescription;
    }
}
