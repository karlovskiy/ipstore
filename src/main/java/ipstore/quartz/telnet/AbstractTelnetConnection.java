package ipstore.quartz.telnet;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.telnet.TelnetClient;

import java.io.IOException;
import java.io.InputStream;

/**
 * Here will be javadoc
 *
 * @author karlovsky
 * @since 3.1, 9/8/13
 */
public abstract class AbstractTelnetConnection implements TelnetConnection {

    protected final TelnetClient telnetClient;

    public AbstractTelnetConnection(TelnetClient telnetClient) {
        this.telnetClient = telnetClient;
    }

    protected String readUntil(InputStream is, String string, int timeout) throws IOException, InterruptedException {
        byte buffer[] = new byte[32];
        long start = System.currentTimeMillis();
        String readbytes = StringUtils.EMPTY;
        while ((readbytes.indexOf(string) < 0) && ((System.currentTimeMillis() - start) < timeout)) {
            if (is.available() > 0) {
                int ret_read = is.read(buffer);
                readbytes = readbytes + new String(buffer, 0, ret_read);
            } else {
                Thread.sleep(500);
            }
        }
        return readbytes;

    }

}
