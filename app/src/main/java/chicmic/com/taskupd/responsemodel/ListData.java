package chicmic.com.taskupd.responsemodel;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ListData {

    @SerializedName("status")
    public int status ;
    @SerializedName("message")
    public String message;
    @SerializedName("data")
    public Data data;
}
