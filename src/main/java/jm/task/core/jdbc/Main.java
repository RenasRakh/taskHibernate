package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;

public class Main {
    private static final UserService userService = new UserServiceImpl();

    public static void main(String[] args) {

        userService.createUsersTable();
        userService.saveUser("Ivan1","Ivanov", (byte)125);
        userService.saveUser("Alex","Walker", (byte)33);
        userService.saveUser("Misha","Kaz", (byte)44);
        userService.saveUser("Ffg","d", (byte)4);

        List<User> list = userService.getAllUsers();
        for (User u : list) {
            System.out.println(u.toString());
        }

        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
