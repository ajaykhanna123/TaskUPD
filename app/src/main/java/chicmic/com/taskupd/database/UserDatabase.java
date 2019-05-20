

package chicmic.com.taskupd.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;


import java.util.ArrayList;
import java.util.List;

import chicmic.com.taskupd.callapi.UserFetchListener;
import chicmic.com.taskupd.datamodel.User;
import chicmic.com.taskupd.helper.Constants;
import chicmic.com.taskupd.helper.Utils;
import chicmic.com.taskupd.responsemodel.CustomerData;


public class UserDatabase extends SQLiteOpenHelper {

    private static final String TAG = UserDatabase.class.getSimpleName();

    public UserDatabase(Context context) {
        super(context, Constants.DATABASE.DB_NAME, null, Constants.DATABASE.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(Constants.DATABASE.CREATE_TABLE_QUERY);
        } catch (SQLException ex) {
            Log.d(TAG, ex.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(Constants.DATABASE.DROP_QUERY);
        this.onCreate(db);
    }

    public void addUser(CustomerData customerData) {

        Log.d("customer", "Values Got " + customerData.id);

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constants.DATABASE.COLUMN_ID, customerData.id);
        values.put(Constants.DATABASE.COLUMN_NAME, customerData.name);

        try {
           // db.insert(Constants.DATABASE.TABLE_NAME, null, values);
            db.insert("user", null, values);
        } catch (Exception e) {

        }
        db.close();
    }
    public List<CustomerData> getUsers()
    {
         SQLiteDatabase mDb =this.getReadableDatabase();
       // Cursor cursor = mDb.rawQuery(Constants.DATABASE.GET_USER_QUERY, null);
        Cursor cursor = mDb.rawQuery("select * from user",null);

        final List<CustomerData> usersList = new ArrayList<>();
        try {
            if (cursor.getCount() > 0) {

                if (cursor.moveToFirst()) {
                    do {
                        CustomerData customerData = new CustomerData();
                        customerData.setFromDatabase(true);
                        customerData.setId(cursor.getString(cursor.getColumnIndex(Constants.DATABASE.COLUMN_ID)));
                        customerData.setName(cursor.getString(cursor.getColumnIndex(Constants.DATABASE.COLUMN_NAME)));


                        usersList.add(customerData);
                        //   publishFlower(customerData);

                    } while (cursor.moveToNext());
                }
            }
        }
        finally {
            cursor.close();
        }
        mDb.close();

        return usersList;
    }

//    public void fetchUsers(UserFetchListener listener) {
//        UserFetcher fetcher = new UserFetcher(listener, this.getWritableDatabase());
//        fetcher.start();
//    }

//    public class UserFetcher extends Thread {
//
//        private final UserFetchListener mListener;
//
//
//        public UserFetcher(UserFetchListener listener, SQLiteDatabase db) {
//            mListener = listener;
//            //mDb = db;
//        }
//
//        @Override
//        public void run() {
//            Cursor cursor = mDb.rawQuery(Constants.DATABASE.GET_USER_QUERY, null);
//
//            final List<CustomerData> usersList = new ArrayList<>();
//
//            if (cursor.getCount() > 0) {
//
//                if (cursor.moveToFirst()) {
//                    do {
//                        CustomerData customerData = new CustomerData();
//                        customerData.setFromDatabase(true);
//                        customerData.setId(cursor.getString(cursor.getColumnIndex(Constants.DATABASE.COLUMN_ID)));
//                        customerData.setName(cursor.getString(cursor.getColumnIndex(Constants.DATABASE.COLUMN_NAME)));
//
//
//                        usersList.add(customerData);
//                        publishFlower(customerData);
//
//                    } while (cursor.moveToNext());
//                }
//            }
//            Handler handler = new Handler(Looper.getMainLooper());
//            handler.post(new Runnable() {
//                @Override
//                public void run() {
//                    mListener.onDeliverAllUsers(usersList);
//                    mListener.onHideDialog();
//                }
//            });
//        }

//        public void publishFlower(final CustomerData customerData) {
//            Handler handler = new Handler(Looper.getMainLooper());
//            handler.post(new Runnable() {
//                @Override
//                public void run() {
//                    mListener.onDeliverUser(customerData);
//                }
//            });
//        }
   // }
}
