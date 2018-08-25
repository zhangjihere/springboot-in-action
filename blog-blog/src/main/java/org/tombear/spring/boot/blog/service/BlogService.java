package org.tombear.spring.boot.blog.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.tombear.spring.boot.blog.domain.Blog;
import org.tombear.spring.boot.blog.domain.User;

import java.util.Optional;

/**
 * <P>Blog Service Interface</P>
 *
 * @author tombear on 2018-08-24 22:23.
 */
public interface BlogService {

    Blog saveBLog(Blog blog);

    void removeBlog(Long id);

    Optional<Blog> getBlogById(Long id);

    /**
     * Pageable to find blog based on user with fuzzy blog's title, order by last
     *
     * @param user     User
     * @param title    fuzzy title
     * @param pageable Pageable Request
     * @return New blog list based on page
     */
    Page<Blog> listBlogsByTitleVote(User user, String title, Pageable pageable);

    /**
     * Pageable to find blog based on user with fuzzy blog's title, order by hot
     *
     * @param user     User
     * @param title    fuzzy title
     * @param pageable Pageable request
     * @return Hot blog list based on page
     */
    Page<Blog> listBlogsByTitleVoteAndSort(User user, String title, Pageable pageable);

    /**
     * reading time increase
     *
     * @param id blog id
     */
    void readingIncrease(Long id);

}
