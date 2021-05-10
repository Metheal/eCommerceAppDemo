package dataAccess.concretes;

import dataAccess.business.UserDao;
import entities.concretes.User;

import java.util.ArrayList;
import java.util.List;

public class InMemoryUserDao implements UserDao {
    private List<User> userList = new ArrayList<>();

    @Override
    public void add(User user) {
        this.userList.add(user);
    }

    @Override
    public void update(User user) {
        User userToUpdate;
        userToUpdate = userList.stream().filter(u -> u.getId() == user.getId()).findFirst().get();
        userToUpdate.setFirstName(user.getFirstName());
        userToUpdate.setLastName(user.getLastName());
        userToUpdate.setEmail(user.getEmail());
        userToUpdate.setPassword(user.getPassword());
        userToUpdate.setStatus(user.getStatus());
    }

    @Override
    public void delete(User user) {
        User userToDelete;
        userToDelete = userList.stream().filter(u -> u.getId() == user.getId()).findFirst().get();
        userList.remove(userToDelete);
    }

    @Override
    public User get(int id) {
        return userList.stream().filter(u -> u.getId() == id).findFirst().get();
    }

    @Override
    public List<User> getAll() {
        return userList;
    }
}
