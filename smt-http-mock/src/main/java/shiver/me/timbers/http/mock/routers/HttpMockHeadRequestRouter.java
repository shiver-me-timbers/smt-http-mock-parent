package shiver.me.timbers.http.mock.routers;

import shiver.me.timbers.http.Headers;
import shiver.me.timbers.http.mock.HttpMockHandler;
import shiver.me.timbers.http.mock.HttpMockResponse;

/**
 * @author Karl Bennett
 */
public class HttpMockHeadRequestRouter implements HttpMockRequestRouter {

    @Override
    public boolean handlesMethod(String method) {
        return "HEAD".equals(method);
    }

    @Override
    public HttpMockResponse route(HttpMockHandler handler, String method, String path) {
        return handler.head(path);
    }

    @Override
    public HttpMockResponse route(HttpMockHandler handler, String method, String path, Headers headers) {
        throw new UnsupportedOperationException();
    }
}
