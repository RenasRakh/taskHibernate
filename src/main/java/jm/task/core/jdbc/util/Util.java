package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    private static final String URL = "jdbc:mysql://localhost:3306/mydbtest?autoReconnect=true&useSSL=false&useUnicode=true&serverTimezone=Europe/Moscow";

    private static final String USERNAME = "root";
    private static final String PASSWORD = "sql4*vjyjkbn3*sS";
    private static SessionFactory sessionFactory;
    private static ServiceRegistry serviceRegistry;

    public static Connection getConnection(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");

            return DriverManager.getConnection(URL,USERNAME,PASSWORD);
        }catch (ClassNotFoundException e) {
            System.err.println("ClassNotFoundException");
        }catch (SQLException e) {
            System.err.println("Не удалось загрузить драйвер");
            e.printStackTrace();
        }
        return null;
    }

    public static SessionFactory getSessionFactory() {
        return getSessionFactory("update");
    }

    public static SessionFactory getSessionFactory(String s ) {
        try {
            Configuration configuration = new Configuration();
            configuration.addAnnotatedClass(User.class);
            Properties properties = new Properties();
            properties.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
            properties.put(Environment.URL, URL);
            properties.put(Environment.USER, "root");
            properties.put(Environment.PASS, "sql4*vjyjkbn3*sS");
            properties.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");
            properties.put(Environment.SHOW_SQL, "true");
            properties.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
            properties.put(Environment.HBM2DDL_AUTO, s);
            configuration.setProperties(properties);
            serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);

        } catch (HibernateException m) {
            m.printStackTrace();
        }
        return sessionFactory;
    }
}
