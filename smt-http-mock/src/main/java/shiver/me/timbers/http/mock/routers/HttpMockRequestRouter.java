package shiver.me.timbers.http.mock.routers;

import shiver.me.timbers.http.Headers;
import shiver.me.timbers.http.mock.HttpMockHandler;
import shiver.me.timbers.http.mock.HttpMockResponse;

/**
 * @author Karl Bennett
 */
public interface HttpMockRequestRouter {

    boolean handlesMethod(String method);

    HttpMockResponse route(HttpMockHandler handler, String method, String path);

    HttpMockResponse route(HttpMockHandler handler, String method, String path, Headers headers);
}
