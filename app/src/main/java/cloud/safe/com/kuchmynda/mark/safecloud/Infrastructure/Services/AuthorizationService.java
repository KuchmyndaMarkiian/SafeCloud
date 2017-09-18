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
        httpClient = new OkHttpClient.Builder().connectTimeout(15, TimeUnit.SECONDS).build();
    }
    public boolean signIn(String url, LoginModel loginModel) {
        /*Gson gsonConverter = new Gson();
        FormBody.Builder bodyBuilder=new FormBody.Builder().add("grant_type","password")
                .add("username",loginModel.getEmail()).add("password",loginModel.getPassword());

        Request.Builder builder = new Request.Builder().url(hostUrl + url)
                .addHeader("Content-Type",ApiConnection.MimeFormUnlencoded)
                .addHeader("Accept",ApiConnection.MimeJson)
                .post(bodyBuilder.build());

        try {
            response = httpClient.newCall(builder.build()).execute();
            if(!response.isSuccessful()){
                throw new Exception(gsonConverter.fromJson(response.body().toString(), ErrorModel.class).errorDescription);
            }
            else return true;
        } catch (Exception e) {
            e.printStackTrace();
            errorModel.errorDescription=e.getMessage();
        }*/
        OkHttpManager manager = new OkHttpManager(ApiConnection.ServerAdress);
        manager.putHeaders(new Tuple<>("Content-Type", ApiConnection.MimeFormUnlencoded),
                new Tuple<>("Accept", ApiConnection.MimeJson));
        manager.putBody(new Tuple<>("grant_type", "password"),
                new Tuple<>("username", loginModel.getEmail()),
                new Tuple<>("password", loginModel.getPassword()));
        manager.post(ApiConnection.LoginAdress);
        response=manager.getResponse();
        if (manager.isSuccessful()) {
            return true;
        } else {
            errorModel.errorDescription = manager.getMessage();
        }
        return false;
    }

    public boolean signUp(String url, RegisterModel registerModel) {
        /*Gson gsonConverter = new Gson();
        FormBody.Builder bodyBuilder=new FormBody.Builder()
                .add("Email",registerModel.getEmail())
                .add("Password",registerModel.getPassword())
                .add("ConfirmPassword",registerModel.getConfirmPassword())
                .add("FirstName",registerModel.getFirstName())
                .add("LastName",registerModel.getLastName());

        Request.Builder builder = new Request.Builder().url(hostUrl + url)
                .addHeader("Content-Type",ApiConnection.MimeFormUnlencoded)
                .post(bodyBuilder.build());

        try {
            response = httpClient.newCall(builder.build()).execute();
            if(!response.isSuccessful()){
                throw new Exception(gsonConverter.fromJson(response.body().toString(), ErrorModel.class).errorDescription);
            }
            else return true;
        } catch (Exception e) {
            e.printStackTrace();
            errorModel.errorDescription=e.getMessage();
        }*/
        OkHttpManager manager = new OkHttpManager(ApiConnection.ServerAdress);
        manager.putHeaders(new Tuple<>("Content-Type", ApiConnection.MimeFormUnlencoded));
        
        manager.putBody(new Tuple<>("grant_type", "password"),
                new Tuple<>("Email", registerModel.getEmail()),
                new Tuple<>("Password", registerModel.getPassword()),
                new Tuple<>("ConfirmPassword", registerModel.getConfirmPassword()),
                new Tuple<>("FirstName", registerModel.getFirstName()),
                new Tuple<>("LastName", registerModel.getLastName()));
        manager.post(ApiConnection.RegisterAdress);
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
