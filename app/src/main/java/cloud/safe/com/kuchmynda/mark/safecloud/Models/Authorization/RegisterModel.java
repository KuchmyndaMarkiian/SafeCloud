package cloud.safe.com.kuchmynda.mark.safecloud.Models.Authorization;

/**
 * Created by Markiian Kuchmynda on 15.09.2017.
 */
public class RegisterModel extends LoginModel{
    private String firstName;
    private String lastName;
    private String confirmPassword;
    private byte[] avatar;

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

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public byte[] getAvatar() {
        return avatar;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }
}
