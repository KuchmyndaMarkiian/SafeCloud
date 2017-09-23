package cloud.safe.com.kuchmynda.mark.safecloud;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import cloud.safe.com.kuchmynda.mark.safecloud.Common.ApiConnection;
import cloud.safe.com.kuchmynda.mark.safecloud.Common.CustomTypes.Tuple;
import cloud.safe.com.kuchmynda.mark.safecloud.Infrastructure.Services.SafeCloudServices.OkHttpManager;
import cloud.safe.com.kuchmynda.mark.safecloud.Models.SafeCloud.Folder;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class DataSelectionFromServer {
    @Test
    public void getNotEmptyFoldrs() throws Exception {
        File file=new File("token.txt");
        if(file.exists()) {
            FileInputStream inputStream=new FileInputStream(file);
            byte[] text=new byte[512];
            inputStream.read(text);
            inputStream.close();
            String token= new String(text);
            OkHttpManager manager = new OkHttpManager(ApiConnection.ServerAddress);
            manager.putHeaders(new Tuple<>("Authorization", "bearer " + token));
            manager.get(ApiConnection.FolderListAddress);
            String response=manager.getStringResult();
            List<Folder> folders = (new Gson()).fromJson(response, new TypeToken<ArrayList<Folder>>() {
            }.getType());
            assertTrue(folders.size() > 0);
        }
    }
}