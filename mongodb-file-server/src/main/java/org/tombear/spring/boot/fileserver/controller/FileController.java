package org.tombear.spring.boot.fileserver.controller;

import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.tombear.spring.boot.fileserver.domain.File;
import org.tombear.spring.boot.fileserver.service.FileService;
import org.tombear.spring.boot.fileserver.util.Utils;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

/**
 * <P>Integrate for MongoDB</P>
 *
 * @author tombear on 2018-08-18 16:57.
 */

@CrossOrigin(origins = "*", maxAge = 3600) // By default all origins are allowed.
@Controller
public class FileController {

    private final FileService fileService;
    @Value("${server.address}")
    private String serverAddress;
    @Value("${server.port}")
    private String serverPort;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @RequestMapping("/")
    public String index(Model model) { // the newest 20 items
        model.addAttribute("files", fileService.listFilesByPage(0, 20));
        return "index";
    }

    /**
     * pageable find
     */
    @RequestMapping("/files/{pageIndex}/{pageSize}")
    public List<File> listFilesByPage(@PathVariable int pageIndex, @PathVariable int pageSize) {
        return fileService.listFilesByPage(pageIndex, pageSize);
    }

    /**
     * download file
     */
    @GetMapping("/files/{id}")
    @ResponseBody
    public ResponseEntity<?> downloadFile(@PathVariable String id) {
        Optional<File> file = fileService.getFileById(id);
        return file
                .<ResponseEntity<?>>map(
                        f -> ResponseEntity.ok()
                                .header((HttpHeaders.CONTENT_DISPOSITION), "attachment; fileName=" + Utils.urlEncode(f.getName(), "UTF-8"))
                                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE)
                                .contentLength(f.getSize())
                                .header(HttpHeaders.CONNECTION, "close")
                                .body(f.getContent().getData()))
                .orElseGet(
                        () -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("File was not found"));
    }

    /**
     * view file
     */
    @GetMapping("/view/{id}")
    public ResponseEntity<?> viewFileOnline(@PathVariable String id) {
        Optional<File> f = fileService.getFileById(id);
        if (f.isPresent()) {
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "fileName=" + Utils.urlEncode(f.get().getName(), "UTF-8"))
                    .header(HttpHeaders.CONTENT_TYPE, f.get().getContentType())
                    .contentLength(f.get().getSize())
                    .header(HttpHeaders.CONNECTION, "close")
                    .body(f.get().getContent().getData());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File was not found");
        }
    }

    /**
     * upload
     */
    @PostMapping("/")
    @ResponseBody
    public String handleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        try {
            File f = new File(null, file.getOriginalFilename(), file.getContentType(), file.getSize(), null, Utils.getMD5(file.getInputStream()), new Binary(file.getBytes()), null);
            fileService.saveFile(f);
        } catch (NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("message", "Your " + file.getOriginalFilename() + " is wrong!");
            return "redirect:/";
        }
        redirectAttributes.addFlashAttribute(("message"), "You successfully uploaded " + file.getOriginalFilename() + "!");
        return "redirect:/";
    }

    /**
     * upload API
     */
    @PostMapping("/uploadFile")
    @ResponseBody
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file) {
        File returnFile;
        try {
            File f = new File(null, file.getOriginalFilename(), file.getContentType(), file.getSize(), null, Utils.getMD5(file.getInputStream()), new Binary(file.getBytes()), null);
            returnFile = fileService.saveFile(f);
            String path = "//" + serverAddress + ":" + serverPort + "/view/" + returnFile.getId();
            return ResponseEntity.status(HttpStatus.OK).body(path);
        } catch (NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFile(@PathVariable String id) {
        try {
            fileService.removeFile(id);
            return ResponseEntity.status(HttpStatus.OK).body("Delete Success!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}
