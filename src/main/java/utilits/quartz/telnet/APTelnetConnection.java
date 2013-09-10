package utilits.quartz.telnet;

import org.apache.commons.lang3.StringUtils;
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
public class APTelnetConnection extends AbstractTelnetConnection {

    private static final Logger logger = LoggerFactory.getLogger(APTelnetConnection.class);
    private final String password;
    private String username;

    public APTelnetConnection(TelnetClient telnetClient, String username, String password) {
        super(telnetClient);
        this.username = username;
        this.password = password;
    }

    @Override
    public TelnetStatus connect(String ip, int port, int timeout) {
        TelnetStatus result = TelnetStatus.TIMEOUT;
        try {
            telnetClient.connect(ip, port);
            InputStream is = telnetClient.getInputStream();
            OutputStream os = telnetClient.getOutputStream();
            if (StringUtils.isEmpty(username)) {
                username = "root";
            }
            String answer = readUntil(is, "login:", timeout);
            if (answer.contains("login:") || answer.contains("Login")) {
                os.write((username + "\r\n").getBytes());
                os.flush();
                answer = readUntil(is, "Password:", timeout);
            }
            if (answer.contains("Password:")) {
                os.write((password + "\r\n").getBytes());
                os.flush();
                answer = readUntil(is, "Login incorrect", timeout);
            }
            if (answer.contains("at tty") || answer.contains(">") || answer.contains("#")) {
                result = TelnetStatus.OK;
            } else if (answer.contains("Login incorrect") || answer.contains("Login")) {
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
