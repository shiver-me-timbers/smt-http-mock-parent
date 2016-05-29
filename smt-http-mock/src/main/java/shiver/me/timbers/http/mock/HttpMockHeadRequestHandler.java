package shiver.me.timbers.http.mock;

import shiver.me.timbers.http.Request;

/**
 * @author Karl Bennett
 */
class HttpMockHeadRequestHandler extends HttpMockMethodRequestHandler {

    HttpMockHeadRequestHandler() {
        super("HEAD");
    }

    @Override
    protected HttpMockResponse handleMethod(HttpMockHandler handler, Request request) {
        return handler.head();
    }
}
