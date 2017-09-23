package cloud.safe.com.kuchmynda.mark.safecloud.Infrastructure;

import android.content.Context;

import cloud.safe.com.kuchmynda.mark.safecloud.Common.CommonData;
import cloud.safe.com.kuchmynda.mark.safecloud.Common.CustomTypes.Tuple;
import cloud.safe.com.kuchmynda.mark.safecloud.Infrastructure.Services.Preferences.AccountPreference;

/**
 * Created by Markiian Kuchmynda on 15.09.2017.
 */

public class UserAccount {
    private static UserAccount instance;

    private String firstName;
    private String lastName;
    private String email;
    private byte[] avatar;
    private Token token;

    private UserAccount() {
    }

    public UserAccount(String email, String name, String surname, byte[] avatar) {
        firstName = name;
        lastName = surname;
        this.email = email;
        this.avatar = avatar;
    }

    public static UserAccount getCurrentAccount() {
        if (instance == null) {
            instance = new UserAccount();
        }
        return instance;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public byte[] getAvatar() {
        return avatar;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public Tuple<String, String> getApiAuthorization() {
        return new Tuple<>("Authorization", String.format("bearer  %s", token.getAccessToken()));
    }

    public static void save(Context contex) {
        if (getCurrentAccount() != null) {
            AccountPreference accountPreference = new AccountPreference(contex.getSharedPreferences(CommonData.UserPreference, Context.MODE_PRIVATE));
            accountPreference.save(instance);
        }
    }

    public static void read(Context context) {
        AccountPreference accountPreference = new AccountPreference(context.getSharedPreferences(CommonData.UserPreference, Context.MODE_PRIVATE));
        if (accountPreference.isLogged())
            instance = accountPreference.read();
    }
}