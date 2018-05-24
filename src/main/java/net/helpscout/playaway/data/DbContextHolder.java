package net.helpscout.playaway.data;

import lombok.experimental.UtilityClass;
import net.helpscout.playaway.AppConfig.DataSourceName;

/**
 * Holds information about which database (master/slave) needs to be used for the given thread.
 */
@UtilityClass
public class DbContextHolder {

    private static final ThreadLocal<DataSourceName> contextHolder = new ThreadLocal<DataSourceName>();

    public static void useSlave() {
        contextHolder.set(DataSourceName.SLAVE);
    }

    public static void useMaster() {
        contextHolder.set(DataSourceName.MASTER);
    }

    public static DataSourceName getDbType() {
        return contextHolder.get();
    }

    public static void reset() {
        contextHolder.remove();
    }
}
