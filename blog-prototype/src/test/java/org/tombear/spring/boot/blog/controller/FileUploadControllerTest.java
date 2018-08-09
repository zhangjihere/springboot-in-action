package org.tombear.spring.boot.blog.controller;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.CharsetUtils;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static com.jayway.jsonpath.matchers.JsonPathMatchers.hasJsonPath;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * <P>Descriptions</P>
 *
 * @author tombear on 2018-07-25 22:48.
 */
public class FileUploadControllerTest {

    @Test
    public void testHttpCall() throws IOException {
        // given
        String boundary = "----HttpClientBoundaryILXaJM9dUxq6suD3";
        HttpPost request = new HttpPost("http://localhost:8080/upload?sessionId=12");
        request.addHeader("Content-Type", "multipart/mixed; boundary=" + boundary);

        StringBody stringBody = new StringBody("{\"id\":1,\"name\":\"tombear\",\"email\":\"zhangjihere@hotmail.com\"}", ContentType.create("application/json", Consts.UTF_8));
        FileBody fileBody = new FileBody(new File("/Users/tombear/Documents/Paw/Request.java"), ContentType.create("multipart/form-data", Consts.UTF_8));

        HttpEntity multiEntity = MultipartEntityBuilder.create()
                .setCharset(CharsetUtils.get("UTF-8"))
                .setMode(HttpMultipartMode.RFC6532)
                .setBoundary(boundary)
                .addPart("metadata", stringBody)
                .addPart("filedata", fileBody)
                .build();
        request.setEntity(multiEntity);

//        FileEntity fileEntity = new FileEntity(,"application/xml; name=\"file-data\"; filename=\"pom.xml\"");
//        StringEntity stringEntity = new StringEntity("{\"id\":1,\"name\":\"tombear\",\"email\":\"zhangjihere@hotmail.com\"}", ContentType.APPLICATION_JSON);

        // when
        HttpResponse response = HttpClientBuilder.create().build().execute(request);

        // then
        HttpEntity entity = response.getEntity();
        String jsonString = EntityUtils.toString(entity);

        // and if the response is
        // {
        //     "status": "OK"
        // }
        // Then we can assert it with
        assertThat(jsonString, hasJsonPath("$.status", is("OK")));
        HttpClientUtils.closeQuietly(response);
    }
}
