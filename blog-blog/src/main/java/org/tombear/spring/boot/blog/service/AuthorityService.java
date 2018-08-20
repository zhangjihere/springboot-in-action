package org.tombear.spring.boot.blog.service;

import org.tombear.spring.boot.blog.domain.Authority;

import java.util.Optional;

/**
 * <P>Authority Service interface</P>
 *
 * @author tombear on 2018-08-12 23:26.
 */
public interface AuthorityService {

    Optional<Authority> getAuthorityById(Long id);
}
