package chicmic.com.taskupd.responsemodel;

import com.google.gson.annotations.SerializedName;

public class FingerData {

    @SerializedName("thumb-image")
    public String thumbImage;
    @SerializedName("main-image")
    public String mainImage;
    @SerializedName("created_at")
    public String createdAt;

}
