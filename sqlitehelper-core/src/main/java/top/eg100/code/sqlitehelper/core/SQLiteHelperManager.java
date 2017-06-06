/*
 * Created by Engine100 on 2017-04-18 14:51:23.
 *
 *      https://github.com/engine100
 *
 */
package top.eg100.code.sqlitehelper.core;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.util.List;

/**
 * A helper class to insert dataList in SQLite with SQLiteStatement
 *
 * @param <T> bean in db
 */
public class SQLiteHelperManager<T> {

    private String mTableName;
    private DataBinder<T> mDataBinder;
    private SQLiteDatabase mDatabase;

    /**
     * init the SQLiteDatabase,
     * e.g. you can call getWritableDatabase in SQLiteOpenHelper
     *
     * @param database database which can be written
     * @return this
     */
    public SQLiteHelperManager<T> initDatabase(SQLiteDatabase database) {
        this.mDatabase = database;
        return this;
    }

    /**
     * init tableName
     *
     * @param tableName which table you want to write data
     * @return this
     */
    public SQLiteHelperManager<T> initTableName(String tableName) {
        this.mTableName = tableName;
        return this;
    }

    /**
     * init databinder
     *
     * @param dataBinder a implement of dataBinder which bind data with fields and values
     * @return this
     */
    public SQLiteHelperManager<T> initDataBinder(DataBinder<T> dataBinder) {
        this.mDataBinder = dataBinder;
        return this;
    }

    /**
     * Create or update dataList in Transaction
     *
     * @param dataList data
     * @return true if success
     * @throws android.database.SQLException If the SQL string is invalid for
     *                                       some reason
     */
    public boolean createOrUpdate(List<T> dataList) {

        if (mTableName == null || mTableName.trim().length() == 0) {
            throw new NullPointerException("mTableName can not be empty or null");
        }
        if (mDatabase == null) {
            throw new NullPointerException("mDatabase can not be empty or null");
        }
        if (mDataBinder == null) {
            throw new NullPointerException("mDataBinder can not be empty or null");
        }

        /**
         * if data is null or empty, return true
         */
        if (dataList == null || dataList.size() == 0) {
            return true;
        }
        String sql = SQLCreator.insertSQL(mTableName, mDataBinder);
        SQLiteStatement insertStatement = mDatabase.compileStatement(sql);

        mDatabase.beginTransaction();
        try {
            for (T data : dataList) {
                ValueBinder.bindValues(data, mDataBinder, insertStatement);
                insertStatement.execute();
            }
            mDatabase.setTransactionSuccessful();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        } finally {
            insertStatement.close();
            mDatabase.endTransaction();
        }
        return true;
    }

}
