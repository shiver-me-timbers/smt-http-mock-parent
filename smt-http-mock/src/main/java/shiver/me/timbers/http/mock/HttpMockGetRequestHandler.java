package shiver.me.timbers.http.mock;

import shiver.me.timbers.http.Request;

/**
 * @author Karl Bennett
 */
class HttpMockGetRequestHandler extends HttpMockMethodRequestHandler {

    HttpMockGetRequestHandler() {
        super("GET");
    }

    @Override
    protected HttpMockResponse handleMethod(HttpMockHandler handler, Request request) {
        return handler.get();
    }
}
