/*
 * Copyright (c) 2015-2016 Filippo Engidashet. All Rights Reserved.
 * <p>
 *  Save to the extent permitted by law, you may not use, copy, modify,
 *  distribute or create derivative works of this material or any part
 *  of it without the prior written consent of Filippo Engidashet.
 *  <p>
 *  The above copyright notice and this permission notice shall be included in
 *  all copies or substantial portions of the Software.
 */

package chicmic.com.taskupd.helper;

/**
 * @author Filippo Engidashet
 * @version 1.0
 * @date today
 */
public class Constants {


    public static final  String BASE_URL="https://updurns.com/api/";
    public static final class DATABASE {

        public static final String DB_NAME = "user";
        public static final int DB_VERSION = 1;
        public static final String TABLE_NAME = "user";
        public static final String COLUMN_ID= "id";
        public static final String COLUMN_NAME = "name";
        //public static final String COLUMN_FIRST_NAME = "first_name";


        public static final String DROP_QUERY = "DROP TABLE IF EXISTS " + TABLE_NAME;

        public static final String GET_USER_QUERY = "SELECT * FROM " + TABLE_NAME;




        public static final String CREATE_TABLE_QUERY = "CREATE TABLE " + TABLE_NAME + "" +
                "(" + COLUMN_ID + " INTEGER PRIMARY KEY not null," +
                COLUMN_NAME + " TEXT not null," +
                ");";
    }






}
