/*
 * Created by Engine100 on 2017-04-19 13:51:23.
 *
 *      https://github.com/engine100
 *
 */
package top.eg100.code.sqlitehelper.core;

import android.database.sqlite.SQLiteStatement;

class ValueBinder {

    public static <T> void bindValues(T data, DataBinder<T> mDataBinder, SQLiteStatement sqLiteStatement) {
        if (mDataBinder.fields().length != mDataBinder.values(data).length) {
            throw new RuntimeException("the fields and values must with the same length!");
        }
        sqLiteStatement.clearBindings();
        Object[] values = mDataBinder.values(data);

        for (int i = values.length; i != 0; i--) {
            Object value = values[i - 1];
            if (value == null) {
                sqLiteStatement.bindNull(i);
                continue;
            }
            if (value instanceof byte[]) {
                sqLiteStatement.bindBlob(i, (byte[]) value);
                continue;
            }
            sqLiteStatement.bindString(i, String.valueOf(value));
        }
    }
}
