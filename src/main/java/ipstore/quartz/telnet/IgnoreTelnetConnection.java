package ipstore.quartz.telnet;

import ipstore.controller.equipment.TelnetStatus;

/**
 * Here will be javadoc
 *
 * @author karlovsky
 * @since 3.1, 9/8/13
 */
public class IgnoreTelnetConnection implements TelnetConnection {

    @Override
    public TelnetStatus connect(String ip, int port, int timeout) {
        return TelnetStatus.IGNORED;
    }
}
