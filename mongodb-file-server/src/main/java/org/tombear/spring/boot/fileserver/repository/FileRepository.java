package org.tombear.spring.boot.fileserver.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.tombear.spring.boot.fileserver.domain.File;

/**
 * <P>MongoDB access interface</P>
 *
 * @author tombear on 2018-08-18 16:19.
 */
public interface FileRepository extends MongoRepository<File, String> {

}
