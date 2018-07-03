package ua.epam.spring.hometask.dao;

import ua.epam.spring.hometask.domain.User;

import java.util.*;

/**
 * Created by Vladyslava_Hubenko on 7/3/2018.
 */
public class UserDAO {

    private static Map<Long, User> userMap;

    public UserDAO() {
        userMap = new HashMap<>();
    }

    public User save(User user) {
        if (user.getId() == null) {
            if (userMap.keySet().size() != 0) {
                user.setId(Collections.max(userMap.keySet()) + 1);
            } else user.setId(1L);
        }
        userMap.put(user.getId(), user);
        return user;
    }

    public void remove(User user) {
        userMap.remove(user.getId());
    }

    public User getById(long id) {
        return userMap.get(id);
    }

    public List<User> getAll() {
        return new ArrayList<>(userMap.values());
    }

    public User getByEmail(String email) {
        Optional<User> user = userMap.values().stream().filter(item -> item.getEmail().equals(email)).findFirst();
        return user.orElse(null);
    }
}
