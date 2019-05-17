package chicmic.com.taskupd.datamodel;

import com.google.gson.annotations.SerializedName;

public class Search {
    @SerializedName("cust_id")
    public String cust_id;
    @SerializedName("pageIndex")
    public String pageIndex;
    @SerializedName("pageSize")
    public String pageSize;
    @SerializedName("search_name")
    public String searchName;


    public Search(String id, String pageIndex,String pageSize,String searchName) {
        this.cust_id = id;
        this.pageIndex = pageIndex;
        this.pageSize=pageSize;
        this.searchName=searchName;
    }
}
