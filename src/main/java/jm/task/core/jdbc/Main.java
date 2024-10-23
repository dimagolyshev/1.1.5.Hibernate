package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {

        UserService userService = new UserServiceImpl();

        userService.createUsersTable();
        userService.saveUser("Chris", "Martin", (byte) 47);
        userService.saveUser("Jonny", "Buckland", (byte) 47);
        userService.removeUserById(10L);
        userService.saveUser("Guy", "Berryman", (byte) 46);
        userService.saveUser("Will", "Champion", (byte) 46);
        userService.getAllUsers();
        userService.cleanUsersTable();
        userService.dropUsersTable();

    }
}
