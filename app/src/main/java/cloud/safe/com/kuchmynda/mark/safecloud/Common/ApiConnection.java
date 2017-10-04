package cloud.safe.com.kuchmynda.mark.safecloud.Common;

/**
 * Created by Markiian Kuchmynda on 15.09.2017.
 */

public final class ApiConnection {
    //region URL Constants

    //public static final String ServerAddress = "http://192.168.0.101:57643";
    public static String ServerAddress = "http://192.168.72.194:57643";
    //region Authorization

    public static final String RegisterAddress = "/api/Account/Register";
    public static final String LoginAddress = "/Token";
    public static String LogoutAddress = "/api/Account/LogOut";
    public static String UserInfo = "/api/Account/AccountInfo";
    public static String AvatarAddress = "/api/Account/UpdateAvatar";
    public static String RestorePasswordAddress = "/api/Account/RestorePassword";

    //endregion

    public static String structureAddress = "/api/Folder/Structure";

    //region Folder

    public static String FolderAddress = "/api/Folder";
    public static final String FolderListAddress = "/api/Folder/List";

    //endregion

    //region File

    public static String DownloadFileAddress = "/api/File/Download";
    public static String FileAddress = "/api/File";

    //endregion

    //endregion

    //region Types

    public static final String MimeJson = "application/json";
    public static final String MimeFormUnlencoded = "application/x-www-form-urlencoded";
    //endregion
}
