package shiver.me.timbers.http.mock;

import shiver.me.timbers.http.Request;

/**
 * @author Karl Bennett
 */
class HttpMockPutRequestHandler extends HttpMockMethodRequestHandler {

    HttpMockPutRequestHandler() {
        super("PUT");
    }

    @Override
    protected HttpMockResponse handleMethod(HttpMockHandler handler, Request request) {
        return handler.put();
    }
}
