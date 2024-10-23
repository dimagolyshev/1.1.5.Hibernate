package jm.task.core.jdbc.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String URL = "jdbc:mysql://localhost:3306/kata";
    private static final String USER = "root";
    private static final String PASSWORD = "1234567812";
    private static volatile Connection connection;

    private static volatile StandardServiceRegistry registry;
    private static volatile SessionFactory sessionFactory;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            synchronized (Util .class) {
                if (connection == null || connection.isClosed()) {
                    connection = DriverManager.getConnection(URL, USER, PASSWORD);
                }
            }
        }
        return connection;
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null || sessionFactory.isClosed()) {
            synchronized (Util .class) {
                if (sessionFactory == null || sessionFactory.isClosed()) {
                    try {
                        Configuration configuration = new Configuration();
                        configuration.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
                        configuration.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/kata");
                        configuration.setProperty("hibernate.connection.username", "root");
                        configuration.setProperty("hibernate.connection.password", "1234567812");
                        configuration.setProperty("hibernate.connection.characterEncoding", "UTF-8");
                        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
                        configuration.setProperty("hibernate.hbm2ddl.auto", "none");
                        configuration.addAnnotatedClass(jm.task.core.jdbc.model.User.class);
                        registry = new StandardServiceRegistryBuilder()
                                .applySettings(configuration.getProperties())
                                .build();
                        sessionFactory = configuration.buildSessionFactory();
                    } catch (Exception e) {
                        StandardServiceRegistryBuilder.destroy(registry);
                        throw new ExceptionInInitializerError(e);
                    }
                }
            }
        }
        return sessionFactory;
    }


}
