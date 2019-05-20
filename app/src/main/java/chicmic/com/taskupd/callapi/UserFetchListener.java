
package chicmic.com.taskupd.callapi;


import java.util.List;

import chicmic.com.taskupd.datamodel.User;
import chicmic.com.taskupd.responsemodel.CustomerData;


public interface UserFetchListener {

    void onDeliverAllUsers(List<CustomerData> users);

    void onDeliverUser(CustomerData user);

    void onHideDialog();
}
