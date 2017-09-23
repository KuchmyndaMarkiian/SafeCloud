package cloud.safe.com.kuchmynda.mark.safecloud.Infrastructure.Services.Preferences;

import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import cloud.safe.com.kuchmynda.mark.safecloud.Infrastructure.UserAccount;

/**
 * Created by Markiian Kuchmynda on 23.09.2017.
 */

public final class AccountPreference {
    private SharedPreferences sharedPreferences;
    private String check="isLogged";
    private String key="currentAccount";
    public AccountPreference(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public void save(UserAccount userAccount) {
        SharedPreferences.Editor editor= sharedPreferences.edit();
        editor.clear();
        Gson gson=new Gson();
        editor.putBoolean(check,true);
        editor.putString(key,gson.toJson(userAccount));
        editor.apply();
    }
    public boolean isLogged() {
        return sharedPreferences.contains(check) && sharedPreferences.getBoolean(check, false);
    }
    public UserAccount read(){
        if(sharedPreferences.contains(key)){
            Gson gson=new Gson();
            return gson.fromJson(sharedPreferences.getString(key,null),new TypeToken<UserAccount>(){}.getType());
        }
        return null;
    }
}
