package shiver.me.timbers.http.servlet;

import shiver.me.timbers.http.Headers;
import shiver.me.timbers.http.Request;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Karl Bennett
 */
class ServletRequestAdaptor implements Request {

    private final HttpServletRequest servletRequest;
    private final ServletHeadersExtractor headersExtractor;

    ServletRequestAdaptor(HttpServletRequest servletRequest) {
        this(servletRequest, new ServletHeadersExtractor());
    }

    ServletRequestAdaptor(HttpServletRequest servletRequest, ServletHeadersExtractor headersExtractor) {
        this.servletRequest = servletRequest;
        this.headersExtractor = headersExtractor;
    }

    @Override
    public String getMethod() {
        return servletRequest.getMethod();
    }

    @Override
    public String getPath() {
        return servletRequest.getPathInfo();
    }

    @Override
    public Headers getHeaders() {
        return headersExtractor.extract(servletRequest);
    }
}
