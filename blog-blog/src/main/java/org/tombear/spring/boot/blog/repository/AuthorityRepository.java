package org.tombear.spring.boot.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tombear.spring.boot.blog.domain.Authority;

/**
 * <P>Repository for Authority entity process</P>
 *
 * @author tombear on 2018-08-12 23:23.
 */
public interface AuthorityRepository extends JpaRepository<Authority, Long> {

}
