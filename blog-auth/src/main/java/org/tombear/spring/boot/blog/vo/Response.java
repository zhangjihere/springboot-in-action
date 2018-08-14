package org.tombear.spring.boot.blog.vo;

import lombok.Value;

/**
 * <P>Response is REST API unified returned value object</P>
 *
 * @author tombear on 2018-08-12 16:50.
 */

@Value
public class Response {
    private boolean success;
    private String message;
    private Object body;
}
