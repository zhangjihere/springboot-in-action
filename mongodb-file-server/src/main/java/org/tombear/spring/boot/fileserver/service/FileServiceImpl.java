package org.tombear.spring.boot.fileserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.tombear.spring.boot.fileserver.domain.File;
import org.tombear.spring.boot.fileserver.repository.FileRepository;

import java.util.List;
import java.util.Optional;

/**
 * <P>Descriptions</P>
 *
 * @author tombear on 2018-08-18 16:38.
 */
@Service
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;

    @Autowired
    public FileServiceImpl(FileRepository fileRepository) {
        Assert.notNull(fileRepository, "Inject Failure");
        this.fileRepository = fileRepository;
    }

    @Override
    public File saveFile(File file) {
        return fileRepository.save(file);
    }

    @Override
    public void removeFile(String id) {
        fileRepository.deleteById(id);
    }

    @Override
    public Optional<File> getFileById(String id) {
        return fileRepository.findById(id);
    }

    @Override
    public List<File> listFilesByPage(int pageIndex, int pageSize) {
        Page<File> page;
        List<File> list;
        Sort sort = new Sort(Sort.Direction.DESC, "uploadDate");
        PageRequest pageable = PageRequest.of(pageIndex, pageSize, sort);
        page = fileRepository.findAll(pageable);
        list = page.getContent();
        return list;
    }
}
