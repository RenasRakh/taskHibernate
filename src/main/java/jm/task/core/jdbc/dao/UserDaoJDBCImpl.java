package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String sqlCommand = "CREATE TABLE users (id BIGINT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(20), lastName VARCHAR(20), age TINYINT)";

        try (Connection connection = Util.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(sqlCommand);
            preparedStatement.executeUpdate();
            System.out.println("Таблица создана.");

        } catch (SQLSyntaxErrorException m) {
            if (m.toString().contains("Table 'users' already exists")){
                System.out.println("Таблица уже существует.");
            } else {
                m.printStackTrace();
            }
        }catch(SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try (Connection connection = Util.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("DROP TABLE users");
            preparedStatement.executeUpdate();
            System.out.println("Таблица удалена.");
        } catch (SQLSyntaxErrorException m){
            if (m.toString().contains("Unknown table 'mydbtest.users")){
                System.out.println("Таблица уже удалена.");
            } else {
                m.printStackTrace();
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT users (name, lastName, age) VALUES ('" + name + "', '"+ lastName +"', " + age +")";
        try (Connection connection = Util.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
            System.out.println("User с именем " + name + " добавлен в базу данных.");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        String sql = "DELETE FROM users WHERE id=" + id;
        try (Connection connection = Util.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
            System.out.println("Пользователь удален.");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        try (Connection connection = Util.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users");

            while(resultSet.next()){
                String name = resultSet.getString(2);
                String lastName = resultSet.getString(3);
                byte age = resultSet.getByte(4);
                list.add(new User(name, lastName, age));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return list;
    }

    public void cleanUsersTable() {
        try (Connection connection = Util.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE from users");
            preparedStatement.executeUpdate();
            System.out.println("Таблица очищена.");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
