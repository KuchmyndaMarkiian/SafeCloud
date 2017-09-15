package cloud.safe.com.kuchmynda.mark.safecloud.Infrastructure.Services;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import cloud.safe.com.kuchmynda.mark.safecloud.Common.ApiConnection;
import cloud.safe.com.kuchmynda.mark.safecloud.Models.Authorization.LoginModel;
import cloud.safe.com.kuchmynda.mark.safecloud.Models.ErrorModel;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
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

    class SignInModel {
        @SerializedName("grant_type")
        public String type = "password";
        public String username;
        public String password;
    }

    public boolean signIn(String url, LoginModel loginModel) {
        Gson gsonConverter = new Gson();
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
