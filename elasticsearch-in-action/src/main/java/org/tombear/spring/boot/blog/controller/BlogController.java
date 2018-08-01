package org.tombear.spring.boot.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.tombear.spring.boot.blog.domain.es.EsBlog;
import org.tombear.spring.boot.blog.repository.es.EsBlogRepository;

import java.util.List;

/**
 * <P>Descriptions</P>
 *
 * @author tombear on 2018-08-01 23:26.
 */
@RestController
@RequestMapping("/blogs")
public class BlogController {
    private final EsBlogRepository esBlogRepository;

    @Autowired
    public BlogController(EsBlogRepository esBlogRepository) {
        this.esBlogRepository = esBlogRepository;
    }

    @GetMapping
    public List<EsBlog> list(@RequestParam(name = "title", required = false, defaultValue = "") String title,
                             @RequestParam(name = "summary", required = false, defaultValue = "") String summary,
                             @RequestParam(name = "content", required = false, defaultValue = "") String content,
                             @RequestParam(name = "pageIndex", required = false, defaultValue = "0") int pageIndex,
                             @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize) {
        // data have been iniialized in TestCase at first, here we retrieve data
        PageRequest pageable = PageRequest.of(pageIndex, pageSize);
        Page<EsBlog> page = esBlogRepository.findByTitleContainingOrSummaryContainingOrContentContaining(title, summary, content, pageable);

        return page.getContent();
    }
}
