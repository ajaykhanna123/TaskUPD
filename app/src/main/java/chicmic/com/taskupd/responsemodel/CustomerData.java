package chicmic.com.taskupd.responsemodel;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class CustomerData {
    @SerializedName("id")
    public String id;
    @SerializedName("name")
    public String name;
    @SerializedName("first_name")
    public String firstName;
    @SerializedName("agent_id")
    public String agentId;
    @SerializedName("dob")
    public String dob;
    @SerializedName("email")
    public String email;
    @SerializedName("finger_data")
    public List<FingerData> fingerData ;
    private boolean isFromDatabase;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public boolean isFromDatabase() {
        return isFromDatabase;
    }

    public void setFromDatabase(boolean fromDatabase) {
        isFromDatabase = fromDatabase;
    }
}
