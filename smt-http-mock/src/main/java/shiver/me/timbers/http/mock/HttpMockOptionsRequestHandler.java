package shiver.me.timbers.http.mock;

import shiver.me.timbers.http.Request;

/**
 * @author Karl Bennett
 */
class HttpMockOptionsRequestHandler extends HttpMockMethodRequestHandler {

    HttpMockOptionsRequestHandler() {
        super("OPTIONS");
    }

    @Override
    protected HttpMockResponse handleMethod(HttpMockHandler handler, Request request) {
        return handler.options();
    }
}
