package utilits.quartz.telnet;

import org.apache.commons.net.telnet.TelnetClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utilits.controller.equipment.TelnetStatus;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Here will be javadoc
 *
 * @author karlovsky
 * @since 3.1, 9/8/13
 */
public class GSTelnetConnection extends AbstractTelnetConnection {

    private static final Logger logger = LoggerFactory.getLogger(GSTelnetConnection.class);
    private final String password;

    public GSTelnetConnection(TelnetClient telnetClient, String password) {
        super(telnetClient);
        this.password = password;
    }

    @Override
    public TelnetStatus connect(String ip, int port, int timeout) {
        TelnetStatus result = TelnetStatus.TIMEOUT;
        telnetClient.setConnectTimeout(timeout / 2);
        try {
            telnetClient.connect(ip, port);
            InputStream is = telnetClient.getInputStream();
            OutputStream os = telnetClient.getOutputStream();
            String answer = readUntil(is, "Password:", timeout);
            if (answer.contains("Password:")) {
                os.write((password + "\r\n").getBytes());
                os.flush();
                answer = readUntil(is, ">", timeout);
            }
            if (answer.contains(">")) {
                result = TelnetStatus.OK;
            } else if (answer.contains("Permission denied, please try again")) {
                result = TelnetStatus.WARNING;
            }
            os.write("exit\r\n".getBytes());
            os.flush();
        } catch (Exception e) {
            logger.error("Error while connect by telnet to equipment with ip={}", ip, e);
        } finally {
            if (telnetClient.isConnected()) {
                try {
                    telnetClient.disconnect();
                } catch (IOException ioe) {
                    logger.error("Error while disconnect by telnet from equipment with ip={}", ip, ioe);
                }
            }
        }
        return result;
    }
}
