/*
 * Created by Engine100 on 2017-04-19 13:46:03.
 *
 *      https://github.com/engine100
 *
 */
package top.eg100.code.sqlitehelper.core;

class SQLCreator {
    static String insertSQL(String mTableName, DataBinder<?> mDataBinder) {
        StringBuilder sb = new StringBuilder();
        String[] fields = mDataBinder.fields();
        sb.append("replace into ").append(mTableName).append(" (");
        for (String field : fields) {
            sb.append(field).append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append(")").append(" values (");
        for (int i = 0; i < fields.length; i++) {
            sb.append("?,");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append(")");

        return sb.toString();
    }
}
