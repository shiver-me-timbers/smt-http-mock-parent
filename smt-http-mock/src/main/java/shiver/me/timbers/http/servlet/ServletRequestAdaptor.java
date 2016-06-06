package shiver.me.timbers.http.servlet;

import shiver.me.timbers.http.Headers;
import shiver.me.timbers.http.Request;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author Karl Bennett
 */
class ServletRequestAdaptor implements Request {

    private final HttpServletRequest servletRequest;
    private final ServletHeadersExtractor headersExtractor;
    private final Streams streams;

    ServletRequestAdaptor(HttpServletRequest servletRequest) {
        this(servletRequest, new ServletHeadersExtractor(), new Streams());
    }

    ServletRequestAdaptor(HttpServletRequest servletRequest, ServletHeadersExtractor headersExtractor, Streams streams) {
        this.servletRequest = servletRequest;
        this.headersExtractor = headersExtractor;
        this.streams = streams;
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
    public boolean hasHeaders() {
        return servletRequest.getHeaderNames().hasMoreElements();
    }

    @Override
    public Headers getHeaders() {
        return headersExtractor.extract(servletRequest);
    }

    @Override
    public boolean hasBody() {
        return servletRequest.getContentLength() > 0;
    }

    @Override
    public String getBodyAsString() {
        try {
            return streams.readToString(servletRequest.getInputStream());
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
