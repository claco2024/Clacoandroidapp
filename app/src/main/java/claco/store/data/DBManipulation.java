package claco.store.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Guanzhu Li on 12/31/2016.
 */
public class DBManipulation {
    private DBHelper mDBHelper;
    private SQLiteDatabase mSQLiteDatabase;
    private Context mContext;
    private int recordNumber;
    private static String mDBName = "";
    private static DBManipulation mInstance;

    public DBManipulation(Context context, String dbname) {
        mDBName = dbname;
        this.mContext = context;
        mDBHelper = new DBHelper(context, dbname);
        mSQLiteDatabase = mDBHelper.getWritableDatabase();
    }

    public static DBManipulation getInstance(Context context, String dbname) {
        if (mInstance == null || !mDBName.equals(dbname)) {
            mInstance = new DBManipulation(context, dbname);
        }
        return mInstance;
    }

    public void insert(Item item) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(mDBHelper.ITEMID, item.getId());
        contentValues.put(mDBHelper.ITEMNAME, item.getName());
        contentValues.put(mDBHelper.IMAGEURL, item.getImageurl());
        contentValues.put(mDBHelper.QUANTITY, item.getQuantity());
        contentValues.put(mDBHelper.PRICE, item.getPrice());
        contentValues.put(mDBHelper.MAXQUANT, item.getMaxQuant());
        long i = mSQLiteDatabase.insert(mDBHelper.TABLENAME, null, contentValues);
        if (i > -1) {
            Toast.makeText(mContext, "Successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(mContext, "Add Failed. Already existed", Toast.LENGTH_SHORT).show();
        }
    }

    public int getQuantity(String id) {
        // get quantity
        Cursor cursor = mSQLiteDatabase.query(mDBHelper.TABLENAME, new String[] {mDBHelper.QUANTITY}, mDBHelper.ITEMID + "=?",
                new String[] {id }, null, null, null);
        //Cursor cursor = mSQLiteDatabase.rawQuery("getQuantity * from " + mDBHelper.TABLENAME + " where " + mDBHelper.ITEMID + "=?",  new String[] {id});
        cursor.moveToFirst();
        if (cursor.getCount() != 0) {
            return cursor.getInt(cursor.getColumnIndex(mDBHelper.QUANTITY));
        } else {
            return 0;
        }
    }

    public int getMaxQuantity(String id) {
        // get max quantity
        Cursor cursor = mSQLiteDatabase.query(mDBHelper.TABLENAME, new String[] {mDBHelper.MAXQUANT}, mDBHelper.ITEMID + "=?",
                new String[] {id }, null, null, null);
        //Cursor cursor = mSQLiteDatabase.rawQuery("getQuantity * from " + mDBHelper.TABLENAME + " where " + mDBHelper.ITEMID + "=?",  new String[] {id});
        cursor.moveToFirst();
        if (cursor.getCount() != 0) {
            return cursor.getInt(cursor.getColumnIndex(mDBHelper.MAXQUANT));
        } else {
            return 0;
        }
    }

    public void update(String id, int quantity) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(mDBHelper.QUANTITY, quantity);
        int res = mSQLiteDatabase.update(mDBHelper.TABLENAME,
                contentValues, mDBHelper.ITEMID + " = ?",
                new String[] {id});
        if (res > 0) {
            Toast.makeText(mContext, "Update successfully", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(mContext, "Update failed, this id does not exist", Toast.LENGTH_LONG).show();
        }
    }

    public void delete(String id) {
        int mark = mSQLiteDatabase.delete(mDBHelper.TABLENAME,mDBHelper.ITEMID + " = ?", new String[] {id});
        if(mark > 0) {
            Toast.makeText(mContext, "remove successfully", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(mContext, "remove failed", Toast.LENGTH_LONG).show();
        }
    }

    public void deleteAll() {
        mSQLiteDatabase.execSQL("delete from "+ mDBHelper.TABLENAME);
    }


    public List<Item> selectAll() {
        List<Item> result = new ArrayList<>();
        Cursor cursor = mSQLiteDatabase.rawQuery("select * from " + mDBHelper.TABLENAME, null);
        // make sure it start the first, before traverse the table
        cursor.moveToFirst();
        if (cursor.getCount() == 0) return result;
        // Cursor: like iteration, it has pointer!
        while (true) {
            String id = cursor.getString(cursor.getColumnIndex(mDBHelper.ITEMID));
            String name = cursor.getString(cursor.getColumnIndex(mDBHelper.ITEMNAME));
            String imageUrl = cursor.getString(cursor.getColumnIndex(mDBHelper.IMAGEURL));
            int maxQuant = cursor.getInt(cursor.getColumnIndex(mDBHelper.MAXQUANT));
            int quant = cursor.getInt(cursor.getColumnIndex(mDBHelper.QUANTITY));
            double price = cursor.getDouble(cursor.getColumnIndex(mDBHelper.PRICE));
            Item temp = new Item(id, name,imageUrl, price, maxQuant,quant);
            result.add(temp);
            if (cursor.moveToNext()) {
                continue;
            } else {
                break;
            }
        }
        return result;
    }


    public int getRecordNumber() {
        Cursor cursor = mSQLiteDatabase.rawQuery("select * from " + mDBHelper.TABLENAME, null);
        return cursor.getCount();
    }

    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }



}
