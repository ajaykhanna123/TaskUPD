package chicmic.com.taskupd.datamodel;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("message")
    public String message;
    @SerializedName("status")
    public int status;
    @SerializedName("customer_id")
    public String customerId;

    public User(String message, int status,String id) {
        this.message = message;
        this.status = status;
        this.customerId=id;
    }
}
