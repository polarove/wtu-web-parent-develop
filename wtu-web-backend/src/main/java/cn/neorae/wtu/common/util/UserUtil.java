package cn.neorae.wtu.common.util;

import cn.neorae.wtu.module.account.domain.User;

import java.util.concurrent.ConcurrentHashMap;

public class UserUtil {

    static ConcurrentHashMap<String, User> userMap = new ConcurrentHashMap<>();

    public static void addUser(User user) {
        userMap.put(user.getUuid(), user);
        userMap.put(user.getEmail(), user);
    }

    public static void removeUser(String uuid) {
        userMap.remove(uuid);
    }

    public static User getUserByUuid(String uuid) {
        return userMap.get(uuid);
    }

    public static User getUserByEmail(String email) {
        return userMap.get(email);
    }
}
