package org.tombear.spring.boot.blog.repository;

import org.tombear.spring.boot.blog.domain.User;

import java.util.List;

/**
 * <P>Descriptions</P>
 *
 * @author tombear on 2018-07-29 13:23.
 */
public interface UserRepository {

    User saveOrUpdateUser(User user);

    void deleteUser(Long id);

    User getUserById(Long id);

    List<User> listUser();
}
