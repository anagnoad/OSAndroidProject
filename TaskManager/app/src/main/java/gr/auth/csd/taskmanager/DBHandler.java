package gr.auth.csd.taskmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import gr.auth.csd.taskmanager.MemoryInfo;

/**
 * Created by Steve Laskaridis on 1/5/2015.
 */
public class DBHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "memoryDB";

    // Contacts table name
    private static final String TABLE_MEMORY_INFO = "memoryInfo";

    // Contacts Table Columns names
    private static final String KEY_DATE = "date";
    private static final String PROPERTY_INTERNAL_MEMORY_FREE = "internalMemoryFree";
    private static final String PROPERTY_INTERNAL_MEMORY_AVAILABLE = "internalMemoryAvailable";
    private static final String PROPERTY_EXTERNAL_MEMORY_FREE = "externalMemoryFree";
    private static final String PROPERTY_EXTERNAL_MEMORY_AVAILABLE = "externalMemoryAvailable";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Called when the database is created for the first time. This is where the
     * creation of tables and the initial population of the tables should happen.
     *
     * @param db The database.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_MEMORY_INFO_TABLE = "CREATE TABLE " + TABLE_MEMORY_INFO + "("
                + KEY_DATE + " DATETIME PRIMARY KEY," + PROPERTY_INTERNAL_MEMORY_FREE + " BIGINT,"
                + PROPERTY_INTERNAL_MEMORY_AVAILABLE + " BIGINT," + PROPERTY_EXTERNAL_MEMORY_FREE + " BIGINT,"
                + PROPERTY_EXTERNAL_MEMORY_AVAILABLE + " BIGINT" + ")";
        db.execSQL(CREATE_MEMORY_INFO_TABLE);
    }

    /**
     * Called when the database needs to be upgraded. The implementation
     * should use this method to drop tables, add tables, or do anything else it
     * needs to upgrade to the new schema version.
     * <p/>
     * <p>
     * The SQLite ALTER TABLE documentation can be found
     * <a href="http://sqlite.org/lang_altertable.html">here</a>. If you add new columns
     * you can use ALTER TABLE to insert them into a live table. If you rename or remove columns
     * you can use ALTER TABLE to rename the old table, then create the new table and then
     * populate the new table with the contents of the old table.
     * </p><p>
     * This method executes within a transaction.  If an exception is thrown, all changes
     * will automatically be rolled back.
     * </p>
     *
     * @param db         The database.
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEMORY_INFO);

        // Create tables again
        onCreate(db);
    }

    public void addRecord(MemoryInfo obj) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        values.put(KEY_DATE, df.format(obj.getDateCaptured()));
        values.put(PROPERTY_INTERNAL_MEMORY_FREE, obj.getInternalMemoryFree());
        values.put(PROPERTY_INTERNAL_MEMORY_AVAILABLE, obj.getInternalMemoryAvailable());
        values.put(PROPERTY_EXTERNAL_MEMORY_FREE, obj.getExternalMemoryFree());
        values.put(PROPERTY_EXTERNAL_MEMORY_AVAILABLE, obj.getExternalMemoryAvailable());

        db.insert(TABLE_MEMORY_INFO, null, values);
        db.close();
    }

    public MemoryInfo getRecord(Date date) throws ParseException {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.query(TABLE_MEMORY_INFO, new String[] { KEY_DATE, PROPERTY_INTERNAL_MEMORY_FREE,
                PROPERTY_INTERNAL_MEMORY_AVAILABLE, PROPERTY_EXTERNAL_MEMORY_FREE, PROPERTY_EXTERNAL_MEMORY_AVAILABLE}, KEY_DATE + "=?",
                new String[] { date.toString() }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        MemoryInfo memoryInfo = new MemoryInfo(df.parse(cursor.getString(0)), Long.parseLong(cursor.getString(1)),
                Long.parseLong(cursor.getString(2)), Long.parseLong(cursor.getString(3)), Long.parseLong(cursor.getString(4)));

        return memoryInfo;
    }

    public List<MemoryInfo> getAllRecords() throws ParseException{
        List<MemoryInfo> records = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_MEMORY_INFO;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                MemoryInfo memoryInfo = new MemoryInfo(df.parse(cursor.getString(0)), Long.parseLong(cursor.getString(1)),
                        Long.parseLong(cursor.getString(2)), Long.parseLong(cursor.getString(3)), Long.parseLong(cursor.getString(4)));
                records.add(memoryInfo);
            }
            while(cursor.moveToNext());
        }
        return records;
    }

    public int getRecordsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_MEMORY_INFO;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

    public int updateRecord() {
        throw new UnsupportedOperationException();
    }

    public void deleteRecord() {
        throw new UnsupportedOperationException();
    }


}
