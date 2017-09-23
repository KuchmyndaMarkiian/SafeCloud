package cloud.safe.com.kuchmynda.mark.safecloud;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import cloud.safe.com.kuchmynda.mark.safecloud.Common.ApiConnection;
import cloud.safe.com.kuchmynda.mark.safecloud.Infrastructure.Services.SafeCloudServices.AuthorizationService;
import cloud.safe.com.kuchmynda.mark.safecloud.Infrastructure.Token;
import cloud.safe.com.kuchmynda.mark.safecloud.Models.Authorization.LoginModel;

import static org.junit.Assert.assertTrue;

/**
 * Created by Markiian Kuchmynda on 21.09.2017.
 */

public class Authorization {
    @Test
    public void isSignedIn() throws Exception {
        AuthorizationService service = new AuthorizationService(ApiConnection.ServerAddress);
        LoginModel model = new LoginModel();
        model.setEmail("harrisonford@gmail.com");
        model.setPassword("Mark95!");
        boolean result = service.signIn(ApiConnection.LoginAddress, model);
        if (result) {
            File file = new File("token.txt");
            if (file.exists()) {
                file.delete();
                file.createNewFile();
            }
            OutputStream outputStreamWriter = new FileOutputStream(file);
            Token token = (new Gson()).fromJson(service.getLastResponseBody(), new TypeToken<Token>() {
            }.getType());
            byte[] bytes = token.getAccessToken().getBytes();
            outputStreamWriter.write(bytes);
            outputStreamWriter.close();
        }
        assertTrue(result);
    }
}
