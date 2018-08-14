package org.tombear.spring.boot.blog.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.tombear.spring.boot.blog.domain.User;

/**
 * <P>UserRepository extends JapRepository instead of CrudRepository, <br/>
 * this enable paginator function</P>
 *
 * @author tombear on 2018-07-29 13:23.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * By user'sanme to find user list with page
     *
     * @param name user's name, not username field
     */
    Page<User> findByNameLike(String name, Pageable pageable);

    /**
     * By username(field) to find user
     *
     * @param username username field, not user's name
     */
    User findByUsername(String username);
}
