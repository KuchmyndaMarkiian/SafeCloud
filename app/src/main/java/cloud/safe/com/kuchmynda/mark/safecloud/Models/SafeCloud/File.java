package cloud.safe.com.kuchmynda.mark.safecloud.Models.SafeCloud;

import android.content.Intent;

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

    @SerializedName("Size")
    private Integer size;
    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}
