package org.tombear.spring.boot.blog.util;

import com.google.common.collect.Lists;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

/**
 * <P>Processor is used to process ConstraintViolationException</P>
 *
 * @author tombear on 2018-08-12 16:59.
 */
public class ConstraintViolationExceptionHandler {

    /**
     * Get exception in batch
     */
    public static String getMessage(ConstraintViolationException e) {
        List<String> msgList = Lists.newArrayList();
        for (ConstraintViolation<?> cv : e.getConstraintViolations()) {
            msgList.add(cv.getMessage());
        }
        return StringUtils.join(msgList.toArray(), ";");
    }
}
