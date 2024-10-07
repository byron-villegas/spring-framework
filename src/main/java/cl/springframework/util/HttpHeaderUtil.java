package cl.springframework.util;

import cl.springframework.constants.Constants;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class HttpHeaderUtil {

    private HttpHeaderUtil() {

    }

    public static HttpHeaders getHttpHeadersByFileType(String name, String type) {
        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.setContentType(MediaType.parseMediaType(Constants.FILE_CONTENT_TYPES.getOrDefault(type.toUpperCase(), MediaType.APPLICATION_OCTET_STREAM_VALUE)));
        httpHeaders.add(Constants.CONTENT_DISPOSITION, String.format(Constants.HEADER_FILENAME_VALUE, name, type.toLowerCase()));

        return httpHeaders;
    }
}