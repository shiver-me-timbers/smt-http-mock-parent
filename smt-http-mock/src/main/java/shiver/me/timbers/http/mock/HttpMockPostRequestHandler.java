package shiver.me.timbers.http.mock;

import shiver.me.timbers.http.Request;

/**
 * @author Karl Bennett
 */
class HttpMockPostRequestHandler extends HttpMockMethodRequestHandler {

    HttpMockPostRequestHandler() {
        super("POST");
    }

    @Override
    protected HttpMockResponse handleMethod(HttpMockHandler handler, Request request) {
        return handler.post();
    }
}
