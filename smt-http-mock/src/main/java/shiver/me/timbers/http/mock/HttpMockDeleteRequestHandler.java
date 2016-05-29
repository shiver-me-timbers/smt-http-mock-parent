package shiver.me.timbers.http.mock;

import shiver.me.timbers.http.Request;

/**
 * @author Karl Bennett
 */
class HttpMockDeleteRequestHandler extends HttpMockMethodRequestHandler {

    HttpMockDeleteRequestHandler() {
        super("DELETE");
    }

    @Override
    protected HttpMockResponse handleMethod(HttpMockHandler handler, Request request) {
        return handler.delete();
    }
}
