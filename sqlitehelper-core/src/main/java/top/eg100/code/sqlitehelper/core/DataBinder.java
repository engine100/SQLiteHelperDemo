/*
 * Created by Engine100 on 2017-04-19 00:41:14.
 *
 *      https://github.com/engine100
 *
 */
package top.eg100.code.sqlitehelper.core;

/**
 * bind the filed and value with bean in db
 *
 * @param <T> bean in db
 */
public interface DataBinder<T> {

    /**
     * fields
     *
     * @return the table columns
     */
    String[] fields();

    /**
     * data
     *
     * @param data bean
     * @return values in bean ,must be as same as filed's order
     */
    Object[] values(T data);

}
