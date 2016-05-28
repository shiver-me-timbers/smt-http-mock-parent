package shiver.me.timbers.http.servlet;

import shiver.me.timbers.http.Response;

import javax.servlet.http.HttpServletResponse;

/**
 * @author Karl Bennett
 */
class ServletResponseWriter {

    void write(HttpServletResponse servletResponse, Response response) {
        servletResponse.setStatus(response.getStatus());
    }
}
