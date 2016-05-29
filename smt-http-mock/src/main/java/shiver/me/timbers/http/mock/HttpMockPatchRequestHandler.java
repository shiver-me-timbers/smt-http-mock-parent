package shiver.me.timbers.http.mock;

import shiver.me.timbers.http.Request;

/**
 * @author Karl Bennett
 */
class HttpMockPatchRequestHandler extends HttpMockMethodRequestHandler {

    HttpMockPatchRequestHandler() {
        super("PATCH");
    }

    @Override
    protected HttpMockResponse handleMethod(HttpMockHandler handler, Request request) {
        return handler.patch();
    }
}
