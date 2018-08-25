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

    public Response(boolean success, String message) {
        this.success = success;
        this.message = message;
        this.body = null;
    }

    public Response(boolean success, String message, Object body) {
        this.success = success;
        this.message = message;
        this.body = body;
    }
}
