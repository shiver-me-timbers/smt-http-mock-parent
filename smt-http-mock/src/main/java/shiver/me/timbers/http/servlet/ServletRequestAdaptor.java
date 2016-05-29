package shiver.me.timbers.http.servlet;

import shiver.me.timbers.http.Request;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Karl Bennett
 */
class ServletRequestAdaptor implements Request {

    private final HttpServletRequest servletRequest;

    ServletRequestAdaptor(HttpServletRequest servletRequest) {
        this.servletRequest = servletRequest;
    }

    @Override
    public String getMethod() {
        return servletRequest.getMethod();
    }

    @Override
    public String getPath() {
        return servletRequest.getPathInfo();
    }
}
