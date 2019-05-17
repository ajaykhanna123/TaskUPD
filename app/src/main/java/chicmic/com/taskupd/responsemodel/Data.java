package chicmic.com.taskupd.responsemodel;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Data {
    @SerializedName("row_count")
    public int rowCount;
    @SerializedName("customer_data")
    public List<CustomerData> customerData =new ArrayList<CustomerData>();

}
