package cloud.safe.com.kuchmynda.mark.safecloud.Models.SafeCloud;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileStructureBase implements Serializable {
    @SerializedName("Id")
    private String id;
    @SerializedName("ParentId")
    private String parentId;
    @SerializedName("Name")
    private String name;
    @SerializedName("Description")
    private String description;
    @SerializedName("DateTime")
    private String dateTime;
    @SerializedName("AttributeHasPublicAccess")
    private boolean hasPublicAccess;

    @SerializedName("Size")
    private Integer size;
    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    @SerializedName("Id")
    public String getId() {
        return id;
    }

    @SerializedName("Id")
    public void setId(String id) {
        this.id = id;
    }

    @SerializedName("ParentId")
    public String getParentId() {
        return parentId;
    }

    @SerializedName("ParentId")
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    @SerializedName("Name")
    public String getName() {
        return name;
    }

    @SerializedName("Name")
    public void setName(String name) {
        this.name = name;
    }

    @SerializedName("Description")
    public String getDescription() {
        return description;
    }

    @SerializedName("Description")
    public void setDescription(String description) {
        this.description = description;
    }

    @SerializedName("DateTime")
    public String getDateTime() {
        return dateTime;
    }

    public Date getDateTimeConverted() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        try {
            return dateFormat.parse(dateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    @SerializedName("DateTime")
    public void setDateTime(String dateTime) {
        this.dateTime=dateTime;
    }

    public boolean isHasPublicAccess() {
        return hasPublicAccess;
    }

    public void setHasPublicAccess(boolean hasPublicAccess) {
        this.hasPublicAccess = hasPublicAccess;
    }
}
