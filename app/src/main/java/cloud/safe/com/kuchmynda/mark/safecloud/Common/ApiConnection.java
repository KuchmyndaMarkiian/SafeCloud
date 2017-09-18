package cloud.safe.com.kuchmynda.mark.safecloud.Common;

/**
 * Created by MARKAN on 15.09.2017.
 */

public final class ApiConnection {
    //region URL Constants

    public static String ServerAdress = "http://192.168.75.220:57643";//"http://192.168.0.106:57643";

    //region Authorization

    public static String RegisterAdress = "/api/Account/Register";
    public static String LoginAdress = "/Token";
    public static String LogoutAdress = "/api/Account/LogOut";
    public static String UserInfo = "/api/Account/AccountInfo";
    public static String AvatarAdress = "/api/Account/UpdateAvatar";
    public static String RestorePasswordAdress = "/api/Account/RestorePassword";

    //endregion

    //region Gallery

    public static String GalleryAdress = "/api/Gallery";
    public static String GalleryListAdress = "/api/Gallery/List";

    //endregion

    //region Picture

    public static String DownloadPictureAdress = "/api/Picture/Download";
    public static String PictureAdress = "/api/Picture";

    //endregion

    //endregion

    //region Types

    public static String MimeJson = "application/json";
    public static String MimeFormUnlencoded = "application/x-www-form-urlencoded";
    //endregion
}
