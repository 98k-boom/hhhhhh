package com.hhhhhh.api.controller;

import javax.servlet.http.HttpServletRequest;

public class BaseController {

    protected boolean isAjax(HttpServletRequest request) {
        return "XMLHttpRequest".equals(request.getHeader("HTTP_X_REQUESTED_WITH"));
    }
}
