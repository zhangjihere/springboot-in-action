package org.tombear.spring.boot.blog.domain;

import com.github.rjeschke.txtmark.Processor;

import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <P>Blog Entity</P>
 *
 * @author tombear on 2018-08-23 22:40.
 */
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Blog implements Serializable {
    private static final long serialVersionUID = -7028331035608131633L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto increment
    private Long id;            // Blog primary identity

    @NotEmpty(message = "title not empty")
    @Size(min = 2, max = 50)
    @Column(nullable = false, length = 50)  // mapped to field, value not-empty
    private String title;

    @NotEmpty(message = "summary not emtpy")
    @Size(min = 2, max = 300)
    @Column(nullable = false)   // mapped to field, value not-empty
    private String summary;

    @Lob    // large object byte, mapped to MySQL's Long Text field type
    @Basic(fetch = FetchType.LAZY)  // "lazy" to load
    @NotEmpty(message = "content not empty")
    @Size(min = 2)
    @Column(nullable = false)   // mapped to field, value not empty
    private String content;

    @Lob    // large object byte, mapped to MySQL's Long Text field type
    @Basic(fetch = FetchType.LAZY)  // "lazy" to load
    @NotEmpty(message = "html content not empty")
    @Size(min = 2)
    @Column(nullable = false)   // mapped to field, value not empty
    private String htmlContent; // transform md to html

    @Column(nullable = false)   // mapped to field, value not empty
    @CreationTimestamp          // auto-create time by DB
    private Timestamp createTime;

    @Column(name = "readSize")
    private Integer readSize = 0;   // read total times

    @Column(name = "commentSize")
    private Integer commentSize = 0;// comment total times

    @Column(name = "voteSize")
    private Integer voteSize = 0;   // click "Like" total times

    @Column(name = "tags", length = 100)
    private String tags;            // tags(type)

    @OneToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;


    public Blog(String title, String summary, String content) {
        this.title = title;
        this.summary = summary;
        this.content = content;
    }

    public void setContent(String content) {
        this.content = content;
        this.htmlContent = Processor.process(content);  // transform Markdown to HTML
    }
}