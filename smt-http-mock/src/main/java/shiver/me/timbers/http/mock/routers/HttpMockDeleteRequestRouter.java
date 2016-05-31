package shiver.me.timbers.http.mock.routers;

import shiver.me.timbers.http.Headers;
import shiver.me.timbers.http.mock.HttpMockHandler;
import shiver.me.timbers.http.mock.HttpMockResponse;

/**
 * @author Karl Bennett
 */
public class HttpMockDeleteRequestRouter implements HttpMockRequestRouter {

    @Override
    public boolean handlesMethod(String method) {
        return "DELETE".equals(method);
    }

    @Override
    public HttpMockResponse route(HttpMockHandler handler, String method, String path) {
        return handler.delete(path);
    }

    @Override
    public HttpMockResponse route(HttpMockHandler handler, String method, String path, Headers headers) {
        throw new UnsupportedOperationException();
    }
}
