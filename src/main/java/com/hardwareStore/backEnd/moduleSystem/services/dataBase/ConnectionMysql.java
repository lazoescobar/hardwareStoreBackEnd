package com.hardwareStore.backEnd.moduleSystem.services.dataBase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

@Component
@ConfigurationProperties(prefix = "datasource")
public class ConnectionMysql {

    Logger logger = Logger.getLogger(ConnectionMysql.class.getSimpleName());
    private static ConnectionMysql instanceConnectionMysql;
    @Autowired
    private Config config;
    private Connection cnx = null;

    public static ConnectionMysql getInstance(){
        return (instanceConnectionMysql == null) ? new ConnectionMysql() : instanceConnectionMysql;
    }
    public Connection open() throws SQLException,  ClassNotFoundException {
        if (cnx == null) {
            try {
                Class.forName(config.getDriver());
                cnx = DriverManager.getConnection(config.getUrl(), config.getUsername(), config.getPassword());
                logger.info(ConnectionMysql.class.getSimpleName()+".open");
            } catch (SQLException SQLEx) {
                System.out.println(SQLEx);
                logger.info(ConnectionMysql.class.getSimpleName()+"."+SQLEx.getMessage());
            } catch (ClassNotFoundException ClaNotFounEx) {
                System.out.println(ClaNotFounEx);
                logger.info(ConnectionMysql.class.getSimpleName()+"."+ClaNotFounEx.getMessage());
            }
        }
        return cnx;
    }

    public void close() throws SQLException {
        if (cnx != null) {
            cnx.close();
            logger.info(ConnectionMysql.class.getSimpleName()+".close");
        }
    }
}
