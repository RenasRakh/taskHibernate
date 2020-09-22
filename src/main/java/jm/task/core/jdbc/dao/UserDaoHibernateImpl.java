package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private Transaction tx = null;


    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        try (Session  session = Util.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            String sqlCommand = "CREATE TABLE users (id BIGINT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(20), lastName VARCHAR(20), age TINYINT)";

            session.createSQLQuery(sqlCommand);

            tx.commit();
            System.out.println("Таблица создана");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session  session = Util.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            String sqlCommand = "DROP TABLE users";

            Query sq = session.createSQLQuery(sqlCommand);
            sq.executeUpdate();
            tx.commit();
            System.out.println("Таблица  удалена");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session  session = Util.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            User user = new User(name, lastName, age);
            session.save(user);
            tx.commit();
            System.out.println("User с именем " + name+ " добавлен в базу данных");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session  session = Util.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.delete(session.get(User.class, id));
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public List<User> getAllUsers() {
        List<User> users = null;
        try (Session  session = Util.getSessionFactory().openSession()) {
            users = (List<User>)  session.createQuery("From User").list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        try (Session  session = Util.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            String sqlCommand = "DELETE from users";

            session.createSQLQuery(sqlCommand).executeUpdate();
            tx.commit();
            System.out.println("Таблица  очищена");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
