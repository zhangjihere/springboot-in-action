package org.tombear.spring.boot.blog.repository.es;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.tombear.spring.boot.blog.domain.es.EsBlog;

/**
 * <P>Descriptions</P>
 *
 * @author tombear on 2018-08-01 22:14.
 */
public interface EsBlogRepository extends ElasticsearchRepository<EsBlog, String> {

    /**
     * pageable search blog
     */
    Page<EsBlog> findByTitleContainingOrSummaryContainingOrContentContaining(String title, String summary, String content, Pageable pageable);
}
