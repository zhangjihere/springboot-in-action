package org.tombear.spring.boot.blog.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.tombear.spring.boot.blog.domain.User;

import java.util.Optional;

/**
 * <P>Provide user service</P>
 *
 * @author tombear on 2018-08-12 16:35.
 */
public interface UserService {

    /**
     * create/edit/save user
     */
    User saveOrUpdateUser(User user);

    /**
     * register user
     */
    User registerUser(User user);

    /**
     * delete user
     */
    void removeUser(Long id);

    /**
     * get user by id
     */
    Optional<User> getUserById(Long id);

    /**
     * By user's name to fuzzily find user with page
     */
    Page<User> listUsersByNameLike(String name, Pageable pageable);
}
