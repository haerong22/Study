package com.example.httpencryption.common;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class ResponseEncryptWrapper extends HttpServletResponseWrapper {

    public ResponseEncryptWrapper(HttpServletResponse response) {
        super(response);
    }
}
