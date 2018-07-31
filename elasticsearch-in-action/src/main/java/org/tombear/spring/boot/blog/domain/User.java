package org.tombear.spring.boot.blog.domain;

import javax.xml.bind.annotation.XmlRootElement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <P>Descriptions</P>
 *
 * @author tombear on 2018-07-24 21:59.
 */
//@XmlRootElement
@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class User {
    private Long id; // entity only identity
    private String name;
    private String email;
}
