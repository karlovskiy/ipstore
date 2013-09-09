package utilits.quartz.telnet;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.telnet.TelnetClient;
import utilits.entity.Equipment;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Here will be javadoc
 *
 * @author karlovsky
 * @since 3.1, 9/7/13
 */
public class TelnetConnectionFactory {

    private static final TelnetConnectionFactory instance = new TelnetConnectionFactory();
    private final TelnetClient telnetClient;
    private final Set<String> gsTypePrefixes;
    private final Set<String> apTypePrefixes;

    private TelnetConnectionFactory() {
        this.telnetClient = new TelnetClient();
        this.gsTypePrefixes = new HashSet<String>();
        this.apTypePrefixes = new HashSet<String>();
    }

    public static TelnetConnectionFactory getInstance() {
        return instance;
    }

    public void setGsPrefixes(String gsPrefixes) {
        addPrefixes(gsTypePrefixes, gsPrefixes);
    }

    public void setApPrefixes(String apPrefixes) {
        addPrefixes(apTypePrefixes, apPrefixes);
    }

    public TelnetConnection getTelnetConnection(Equipment equipment) {
        TelnetConnection telnetConnection;
        String type = equipment.getType();
        String password = equipment.getPassword();
        Boolean isTelnetCheck = equipment.getTelnetCheck();
        if (hasPrefix(type, gsTypePrefixes) && Boolean.TRUE.equals(isTelnetCheck)) {
            telnetConnection = new GSTelnetConnection(telnetClient, password);
        } else if (hasPrefix(type, apTypePrefixes) && Boolean.TRUE.equals(isTelnetCheck)) {
            String username = equipment.getUsername();
            telnetConnection = new APTelnetConnection(telnetClient, username, password);
        } else {
            telnetConnection = new IgnoreTelnetConnection();
        }
        return telnetConnection;
    }

    private void addPrefixes(Set<String> set, String prefixes) {
        String[] types = prefixes.split(",");
        for (String t : types) {
            set.add(t.trim());
        }
    }

    private boolean hasPrefix(String type, Collection<String> prefixes) {
        if (StringUtils.isNotEmpty(type)) {
            for (String prefix : prefixes) {
                if (StringUtils.startsWithIgnoreCase(type, prefix)) {
                    return true;
                }
            }
        }
        return false;
    }

}
