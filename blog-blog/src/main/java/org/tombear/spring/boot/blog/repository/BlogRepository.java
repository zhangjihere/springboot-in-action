package org.tombear.spring.boot.blog.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.tombear.spring.boot.blog.domain.Blog;
import org.tombear.spring.boot.blog.domain.User;

/**
 * <P>Blog repository interface</P>
 *
 * @author tombear on 2018-08-23 23:36.
 */
public interface BlogRepository extends JpaRepository<Blog, Long> {

    /**
     * find blog pageable list by user, blog title
     */
    Page<Blog> findByUserAndTitleLike(User user, String title, Pageable pageable);

    /**
     * find blog pageable list by fuzzy user and title or fuzzy user and tags, and orber by create time desc
     */
    Page<Blog> findByTitleLikeAndUserOrTagsLikeAndUserOrderByCreateTimeDesc(String title, User user, String tags, User user2, Pageable pageable);
}
