package org.tombear.spring.boot.fileserver.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5 tool
 */
public class Utils {

    /**
     * compute MD5 from inputstream
     */
    public static String getMD5(InputStream is) throws NoSuchAlgorithmException, IOException {
        StringBuilder md5 = new StringBuilder();
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] dataBytes = new byte[1024];

        int nread = 0;
        while ((nread = is.read(dataBytes)) != -1) {
            md.update(dataBytes, 0, nread);
        }
        is.close();
        byte[] mdbytes = md.digest();

        // convert the byte to hex format  
        for (int i = 0; i < mdbytes.length; i++) {
            md5.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        return md5.toString();
    }

    public static String urlEncode(String content, String charset) {
        try {
            return URLEncoder.encode(content, charset);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return content;
    }

}
