package chicmic.com.taskupd.datamodel;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class User implements Serializable {
    @SerializedName("message")
    public String message;
    @SerializedName("status")
    public int status;
    @SerializedName("customer_id")
    public String customerId;


    public User(String message, int status, String id) {
        this.message = message;
        this.status = status;
        this.customerId=id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }


}
