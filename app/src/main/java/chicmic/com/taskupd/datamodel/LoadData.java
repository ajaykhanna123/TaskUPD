package chicmic.com.taskupd.datamodel;

import com.google.gson.annotations.SerializedName;

public class LoadData {
    @SerializedName("cust_id")
    public String cust_id;
    @SerializedName("pageIndex")
    public String pageIndex;
    @SerializedName("pageSize")
    public String pageSize;


    public LoadData(String id, String pageIndex,String pageSize) {
        this.cust_id = id;
        this.pageIndex = pageIndex;
        this.pageSize=pageSize;
    }
}
