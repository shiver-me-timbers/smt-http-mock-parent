package shiver.me.timbers.http.mock;

import shiver.me.timbers.http.Request;

/**
 * @author Karl Bennett
 */
class HttpMockTraceRequestHandler extends HttpMockMethodRequestHandler {

    HttpMockTraceRequestHandler() {
        super("TRACE");
    }

    @Override
    protected HttpMockResponse handleMethod(HttpMockHandler handler, Request request) {
        return handler.trace();
    }
}
