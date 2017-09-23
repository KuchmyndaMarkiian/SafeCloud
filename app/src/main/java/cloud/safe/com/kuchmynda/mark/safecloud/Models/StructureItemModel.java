package cloud.safe.com.kuchmynda.mark.safecloud.Models;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Markiian Kuchmynda on 14.09.2017.
 */

public class StructureItemModel implements Serializable{
    private String id;
    private String parentId;
    private String header;
    private Date date;

    public StructureItemModel(String id, String parentId, String header, Date date){
        this.parentId = parentId;
        this.date=date;
        this.header=header;
        this.id=id;
    }
    public String getHeader() {
        return header;
    }

    public Date getDate() {
        return date;
    }

    public String getId() {
        return id;
    }

    public String getParentId() {
        return parentId;
    }
}
