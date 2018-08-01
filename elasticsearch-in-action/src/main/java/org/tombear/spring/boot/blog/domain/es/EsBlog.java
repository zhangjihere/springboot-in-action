package org.tombear.spring.boot.blog.domain.es;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;

import lombok.Data;

/**
 * <P>Descriptions</P>
 *
 * @author tombear on 2018-08-01 22:03.
 */
@Document(indexName = "blog", type = "blog")
@Data
public class EsBlog implements Serializable {
    private static final long serialVersionUID = -8618488883009257829L;
    @Id
    private String id; // primary key, elasticsearch pk use String
    private String title;
    private String summary;
    private String content;

    protected EsBlog() {
    }

    public EsBlog(String title, String summary, String content) {
        this.title = title;
        this.summary = summary;
        this.content = content;
    }
}
