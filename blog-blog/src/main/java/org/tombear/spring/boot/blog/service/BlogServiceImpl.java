package org.tombear.spring.boot.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.tombear.spring.boot.blog.domain.Blog;
import org.tombear.spring.boot.blog.domain.User;
import org.tombear.spring.boot.blog.repository.BlogRepository;

import java.util.Optional;

import javax.transaction.Transactional;

/**
 * <P>Descriptions</P>
 *
 * @author tombear on 2018-08-24 22:39.
 */
@Service
public class BlogServiceImpl implements BlogService {

    private final BlogRepository blogRepository;

    @Autowired
    public BlogServiceImpl(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    @Transactional
    @Override
    public Blog saveBLog(Blog blog) {
        return blogRepository.save(blog);
    }

    @Transactional
    @Override
    public void removeBlog(Long id) {
        blogRepository.deleteById(id);
    }

    @Override
    public Optional<Blog> getBlogById(Long id) {
        return blogRepository.findById(id);
    }

    @Override
    public Page<Blog> listBlogsByTitleVote(User user, String title, Pageable pageable) {
        String fuzzyTitle = "%" + title + "%";
        return blogRepository.findByTitleLikeAndUserOrTagsLikeAndUserOrderByCreateTimeDesc(fuzzyTitle, user, "", user, pageable);
    }

    @Override
    public Page<Blog> listBlogsByTitleVoteAndSort(User user, String title, Pageable pageable) {
        String fuzzyTitle = "%" + title + "%";
        return blogRepository.findByUserAndTitleLike(user, fuzzyTitle, pageable);
    }

    @Override
    public void readingIncrease(Long id) {
        Optional<Blog> optionalBlog = blogRepository.findById(id);
        optionalBlog.ifPresent(blog -> {
            blog.setReadSize(blog.getReadSize() + 1);
            this.saveBLog(blog);
        });
    }
}
