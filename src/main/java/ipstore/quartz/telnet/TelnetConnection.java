package ipstore.quartz.telnet;

import ipstore.controller.equipment.TelnetStatus;

/**
 * Here will be javadoc
 *
 * @author karlovsky
 * @since 3.1, 9/7/13
 */
public interface TelnetConnection {

    public TelnetStatus connect(String ip, int port, int timeout);

}
