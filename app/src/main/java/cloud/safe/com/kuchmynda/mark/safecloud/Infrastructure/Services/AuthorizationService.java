package cloud.safe.com.kuchmynda.mark.safecloud.Infrastructure.Services;

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
 * Created by MARKAN on 15.09.2017.
 */

public class AuthorizationService {
    OkHttpClient httpClient;
    String hostUrl;
    ErrorModel errorModel;
    Response response;


    public AuthorizationService(String hostUrl) {
        errorModel = new ErrorModel();
        this.hostUrl = hostUrl;
        httpClient = new OkHttpClient.Builder().connectTimeout(60, TimeUnit.SECONDS).build();
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
        
        manager.putBody(new Tuple<>("grant_type", "password"),
                new Tuple<>("Email", registerModel.getEmail()),
                new Tuple<>("Password", registerModel.getPassword()),
                new Tuple<>("ConfirmPassword", registerModel.getConfirmPassword()),
                new Tuple<>("FirstName", registerModel.getFirstName()),
                new Tuple<>("LastName", registerModel.getLastName()));
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
