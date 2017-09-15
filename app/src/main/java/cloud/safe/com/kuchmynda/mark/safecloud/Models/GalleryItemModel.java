package cloud.safe.com.kuchmynda.mark.safecloud.Models;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by MARKAN on 14.09.2017.
 */

public class GalleryItemModel implements Serializable{
    private String header;
    private Date date;
    private int count;

    public GalleryItemModel(String header, Date date, int count){
        this.count=count;
        this.date=date;
        this.header=header;
    }
    public String getHeader() {
        return header;
    }

    public Date getDate() {
        return date;
    }

    public int getCount() {
        return count;
    }
}
