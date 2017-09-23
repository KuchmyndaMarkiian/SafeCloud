package cloud.safe.com.kuchmynda.mark.safecloud.Models.SafeCloud;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Folder extends FileStructureBase implements Serializable {

    @SerializedName("Files")
    private List<File> files = new ArrayList<>();


    @SerializedName("Files")
    public List<File> getFiles() {
        return files;
    }

    @SerializedName("Files")
    public void setFiles(List<File> files) {
        this.files = files;
    }
}
