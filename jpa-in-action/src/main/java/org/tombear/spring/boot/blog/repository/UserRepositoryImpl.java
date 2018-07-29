package org.tombear.spring.boot.blog.repository;

import com.google.common.collect.Lists;

import org.springframework.stereotype.Repository;
import org.tombear.spring.boot.blog.domain.User;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * <P>Descriptions</P>
 *
 * @author tombear on 2018-07-29 13:25.
 */
@Repository
public class UserRepositoryImpl implements UserRepository {

    private static AtomicLong counter = new AtomicLong();
    private final ConcurrentMap<Long, User> userMap = new ConcurrentHashMap<>();

    {
        User user = User.of(counter.incrementAndGet(), "Kavin", "kavin@tt.com");
        userMap.put(user.getId(), user);
        user = User.of(counter.incrementAndGet(), "Jacky", "Jacky@qq.com");
        userMap.put(user.getId(), user);
        user = User.of(counter.incrementAndGet(), "Dodge", "Dodge@dd.com");
        userMap.put(user.getId(), user);
    }

    @Override
    public User saveOrUpdateUser(User user) {
        Long id = user.getId();
        if (id == null) {
            id = counter.incrementAndGet();
            user.setId(id);
        }
        this.userMap.put(id, user);
        return user;
    }

    @Override
    public void deleteUser(Long id) {
        this.userMap.remove(id);
    }

    @Override
    public User getUserById(Long id) {
        return userMap.get(id);
    }

    @Override
    public List<User> listUser() {
        return Lists.newArrayList(userMap.values());
    }
}
