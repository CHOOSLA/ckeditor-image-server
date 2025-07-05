package org.duckdns.choosla.ckeditor_image_server.common.exception;

import org.springframework.http.HttpStatus;

public class BadIOExecption extends BaseException{
    public BadIOExecption(HttpStatus statusCode) {
        super(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public BadIOExecption(HttpStatus statusCode, String responseMessage) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, responseMessage);
    }
}
