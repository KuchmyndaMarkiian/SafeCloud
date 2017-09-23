package cloud.safe.com.kuchmynda.mark.safecloud.Infrastructure;

import com.google.gson.annotations.SerializedName;

public class Token {
    @SerializedName("access_token")
    private String AccessToken;

    @SerializedName("token_type")
    private String TokenType;

    @SerializedName("userName")
    private String UserName;

    @SerializedName(".expires")
    private String Expires;

    public String getAccessToken() {
        return AccessToken;
    }

    public void setAccessToken(String accessToken) {
        AccessToken = accessToken;
    }

    public String getTokenType() {
        return TokenType;
    }

    public void setTokenType(String tokenType) {
        TokenType = tokenType;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getExpires() {
        return Expires;
    }

    public void setExpires(String expires) {
        Expires = expires;
    }
}
