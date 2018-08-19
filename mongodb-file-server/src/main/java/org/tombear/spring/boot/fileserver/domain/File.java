package org.tombear.spring.boot.fileserver.domain;

import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * <P>Define file document for MongoDB</P>
 *
 * @author tombear on 2018-08-18 15:56.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = {"id", "content"})
@Document
public class File {

    @Id
    private String id;          // primary key
    private String name;        // file name
    private String contentType; // file type
    private long size;
    private Date uploadDate;
    private String md5;
    private Binary content;     // file content
    private String path;        // file path

}
