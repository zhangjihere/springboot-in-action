package org.tombear.spring.boot.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tombear.spring.boot.blog.domain.Authority;
import org.tombear.spring.boot.blog.repository.AuthorityRepository;

import java.util.Optional;

/**
 * <P>Descriptions</P>
 *
 * @author tombear on 2018-08-12 23:27.
 */
@Service
public class AuthorityServiceImpl implements AuthorityService {

    private final AuthorityRepository authorityRepository;

    @Autowired
    public AuthorityServiceImpl(AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
    }

    @Override
    public Optional<Authority> getAuthorityById(Long id) {
        return authorityRepository.findById(id);
    }
}
