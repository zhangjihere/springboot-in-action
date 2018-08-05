package org.tombear.spring.boot.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Part;

/**
 * <P>Descriptions</P>
 *
 * @author tombear on 2018-07-25 21:24.
 */
@Controller
public class FileUploadController {
    @RequestMapping("/form")
    public String handleFormUpload(@RequestParam String name, @RequestPart("file") MultipartFile file) {
        if (file.isEmpty()) {
            System.out.println("name = [" + name + "], file = [" + file + "]");
            return "redirect:uploadFailure";
        } else {
            System.out.println("name = [" + name + "], file = [" + file + "]");
            System.out.println(file.getSize());
            return "redirect:uploadSuccess";
        }
    }

    /**
     * Notice, the file @RequestPar type use javax.servlet.http.Part.
     * If use org.springframework.web.multipart.MultipartFile, Use HttpComponents Httpclient send Multipart post request,
     * It will occur "Bad Request","message":"Required String parameter, about some argument specify MultipartFile type.
     */
    @PostMapping("/upload")
    public String onSubmit(@RequestParam("sessionId") String sessionId,
                           @RequestPart(value = "metadata", required = false) String metaData,
                           @RequestPart(value = "filedata", required = false) Part file) {
        System.out.println("sessionId = [" + sessionId + "], file = [" + file + "], metaData = [" + metaData + "]");
//        System.out.println("sessionId = [" + sessionId + "], metaData = [" + metaData + "]");
        return "redirect:uploadRest";
    }

}
