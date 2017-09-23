package cloud.safe.com.kuchmynda.mark.safecloud.Models.SafeCloud;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class File extends FileStructureBase implements Serializable {

    @SerializedName("Geolocation")
    private String geolocation;

    @SerializedName("Geolocation")
    public String getGeolocation() {
        return geolocation;
    }

    @SerializedName("Geolocation")
    public void setGeolocation(String geolocation) {
        this.geolocation = geolocation;
    }

}
