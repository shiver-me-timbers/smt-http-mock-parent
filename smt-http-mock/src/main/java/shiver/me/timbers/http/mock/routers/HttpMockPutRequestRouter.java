package shiver.me.timbers.http.mock.routers;

import shiver.me.timbers.http.Headers;
import shiver.me.timbers.http.mock.HttpMockHandler;
import shiver.me.timbers.http.mock.HttpMockResponse;

import static shiver.me.timbers.http.Methods.PUT;

/**
 * @author Karl Bennett
 */
public class HttpMockPutRequestRouter implements HttpMockRequestRouter {

    @Override
    public boolean handlesMethod(String method) {
        return PUT.equals(method);
    }

    @Override
    public HttpMockResponse route(HttpMockHandler handler, String method, String path) {
        return handler.put(path);
    }

    @Override
    public HttpMockResponse route(HttpMockHandler handler, String method, String path, Headers headers) {
        return handler.put(path, headers);
    }
}
