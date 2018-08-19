package org.tombear.spring.boot.fileserver.service;

import org.tombear.spring.boot.fileserver.domain.File;

import java.util.List;
import java.util.Optional;

/**
 * <P>Descriptions</P>
 *
 * @author tombear on 2018-08-18 16:33.
 */
public interface FileService {

    File saveFile(File file);

    void removeFile(String id);

    Optional<File> getFileById(String id);

    /**
     * pagable search by uploadDate desc
     */
    List<File> listFilesByPage(int pageIndex, int pageSize);
}
