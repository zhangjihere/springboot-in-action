package org.tombear.spring.boot.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.tombear.spring.boot.blog.domain.User;
import org.tombear.spring.boot.blog.repository.UserRepository;

import java.util.Optional;

import javax.transaction.Transactional;

/**
 * <P>UserService implement</P>
 *
 * @author tombear on 2018-08-12 16:41.
 */
@Service("userServiceImpl")
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * create/edit/save user
     */
    @Transactional
    @Override
    public User saveOrUpdateUser(User user) {
        return userRepository.save(user);
    }

    /**
     * register user
     */
    @Transactional
    @Override
    public User registerUser(User user) {
        return userRepository.save(user);
    }

    /**
     * delete user
     */
    @Transactional
    @Override
    public void removeUser(Long id) {
        userRepository.deleteById(id);
    }

    /**
     * get user by id
     */
    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    /**
     * By user's name to fuzzily find user with page. <br/> the name will be compose as %name%
     */
    @Override
    public Page<User> listUsersByNameLike(String name, Pageable pageable) {
        name = "%" + name + "%";
        return userRepository.findByNameLike(name, pageable);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

}
