
/*
 * Copyright 2002-2005 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package utilits.db;

import org.hsqldb.persist.HsqlProperties;
import org.hsqldb.server.ServerConfiguration;
import org.hsqldb.server.ServerConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Bean that will start an instance of an HSQL database.  This class is primarily intended
 * to be used in demo applications.  It allows for a self contained distribution including
 * a database instance.  The DataSource reference is necessary for proper shutdown.
 * <p/>
 * This is an example of a bean configuration:
 * <p/>
 * <pre>
 *     &lt;bean id="dataBase" class="org.springmodules.db.hsqldb.ServerBean" singleton="true" lazy-init="false"&gt;
 *         &lt;property name="dataSource"&gt;&lt;ref local="dataSource"/&gt;&lt;/property&gt;
 *         &lt;property name="serverProperties"&gt;
 *             &lt;props&gt;
 *                 &lt;prop key="server.port"&gt;9101&lt;/prop&gt;
 *                 &lt;prop key="server.database.0"&gt;webapps/myapp/db/test&lt;/prop&gt;
 *                 &lt;prop key="server.dbname.0"&gt;test&lt;/prop&gt;
 *             &lt;/props&gt;
 *         &lt;/property&gt;
 *     &lt;/bean&gt;
 * </pre>
 *
 * @author Thomas Risberg
 * @see org.hsqldb.Server
 */

public class ServerBean implements InitializingBean, DisposableBean {

    /**
     * Commons Logging instance.
     */
    private static final Logger logger = LoggerFactory.getLogger(ServerBean.class);

    /**
     * Properties used to customize instance.
     */
    private Properties serverProperties;

    /**
     * The actual server instance.
     */
    private org.hsqldb.Server server;

    /**
     * DataSource used for shutdown.
     */
    private DataSource dataSource;

    public Properties getServerProperties() {
        return serverProperties;
    }

    public void setServerProperties(Properties serverProperties) {
        this.serverProperties = serverProperties;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void afterPropertiesSet() throws Exception {


        HsqlProperties configProps = new HsqlProperties(serverProperties);
        if (configProps == null) {
            configProps = new HsqlProperties();
        }

        ServerConfiguration.translateDefaultDatabaseProperty(configProps);

        // finished setting up properties - set some important behaviors as well;
        server = new org.hsqldb.Server();
        server.setRestartOnShutdown(false);
        server.setNoSystemExit(true);
        server.setProperties(configProps);

        logger.info("HSQL Server Startup sequence initiated");

        server.start();

        String portMsg = "port " + server.getPort();
        logger.info("HSQL Server listening on " + portMsg);
    }

    public void destroy() {

        logger.info("HSQL Server Shutdown sequence initiated");
        if (dataSource != null) {
            Connection con = null;
            try {
                con = dataSource.getConnection();
                con.createStatement().execute("SHUTDOWN");
            } catch (SQLException e) {
                logger.error("HSQL Server Shutdown failed: " + e.getMessage());
            } finally {
                try {
                    if (con != null)
                        con.close();
                } catch (Exception ignore) {
                }
            }
        } else {
            logger.warn("HSQL ServerBean needs a dataSource property set to shutdown database safely.");
        }
        server.signalCloseAllServerConnections();
        int status = server.stop();
        long timeout = System.currentTimeMillis() + 5000;
        while (status != ServerConstants.SERVER_STATE_SHUTDOWN && System.currentTimeMillis() < timeout) {
            try {
                Thread.sleep(100);
                status = server.getState();
            } catch (InterruptedException e) {
                logger.error("Error while shutting down HSQL Server: " + e.getMessage());
                break;
            }
        }
        if (status != ServerConstants.SERVER_STATE_SHUTDOWN) {
            logger.warn("HSQL Server failed to shutdown properly.");
        } else {
            logger.info("HSQL Server Shutdown completed");
        }
        server = null;

    }

}