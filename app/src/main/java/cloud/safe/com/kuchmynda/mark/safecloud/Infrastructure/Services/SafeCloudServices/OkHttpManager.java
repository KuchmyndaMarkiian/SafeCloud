package cloud.safe.com.kuchmynda.mark.safecloud.Infrastructure.Services.SafeCloudServices;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import cloud.safe.com.kuchmynda.mark.safecloud.Common.ApiConnection;
import cloud.safe.com.kuchmynda.mark.safecloud.Common.CustomTypes.Tuple;
import cloud.safe.com.kuchmynda.mark.safecloud.Models.ErrorModel;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Markiian Kuchmynda on 18.09.2017.
 */
//todo: need test it!!!!!
public class OkHttpManager {
    private final String hostUrl;
    private final OkHttpClient client;
    private Response response;
    private final Gson converter;
    private RequestBody requestBody;
    private Request.Builder requestBuilder;
    private String message = "";

    public OkHttpManager(String hostUrl) {
        this.hostUrl = hostUrl;
        client = new OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS).build();
        converter = new Gson();
        requestBuilder=new Request.Builder();
    }

    public Response getResponse() {
        return response;
    }

    //region Body & headers
    @SafeVarargs
    public final void putHeaders(Tuple<String, String>... pairs) {
        requestBuilder = new Request.Builder();
        for (Tuple<String, String> pair : pairs) {
            requestBuilder = requestBuilder.addHeader(pair.getKey(), pair.getValue());
        }
    }

    @SafeVarargs
    public final void putBody(Tuple<String, String>... pairs) {
        FormBody.Builder builder = new FormBody.Builder();
        for (Tuple<String, String> pair : pairs) {
            builder = builder.add(pair.getKey(), pair.getValue());
        }
        requestBody = builder.build();
    }

    public <T> void putBody(T object) {
        String json = converter.toJson(object);
        requestBody = RequestBody.create(MediaType.parse(ApiConnection.MimeJson), json);
    }

    //endregion
    //region Methods
    public void post(String url) {
        requestBuilder = requestBuilder.url(hostUrl + url).post(requestBody);
        send();
    }

    public void get(String url) {
        requestBuilder = requestBuilder.url(hostUrl + url);
        send();
    }

    public void put(String url) {
        requestBuilder = requestBuilder.url(hostUrl + url).put(requestBody);
        send();
    }

    public void patch(String url) {
        requestBuilder = requestBuilder.url(hostUrl + url).patch(requestBody);
        send();
    }

    public void delete(String url) {
        requestBuilder = requestBuilder.url(hostUrl + url).delete(requestBody);
        send();
    }

    //endregion
    private void send() {
        try {
            response = client.newCall(requestBuilder.build()).execute();
            if (!isSuccessful())
                throw new Exception(
                        converter.fromJson(response.body().string(), ErrorModel.class)
                                .errorDescription);
        } catch (Exception e) {
            e.printStackTrace();
            message = e.getMessage();
        }
    }

    public String getMessage() {
        return message;
    }

    public boolean isSuccessful() {
        return response.isSuccessful();
    }

    public String getStringResult() {
        try {
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
   /* public Stream getStreamResult(){
        return  response.body().byteStream();
    }*/
}