package shiver.me.timbers.http.mock;

import shiver.me.timbers.http.Headers;

/**
 * @author Karl Bennett
 */
class HttpMockTraceRequestHandler extends HttpMockMethodRequestHandler {

    HttpMockTraceRequestHandler(HttpMockHeaderFilter headerFilter) {
        super("TRACE", headerFilter);
    }

    @Override
    protected HttpMockResponse handleMethod(HttpMockHandler handler, String path) {
        return handler.trace(path);
    }

    @Override
    protected HttpMockResponse handleMethod(HttpMockHandler handler, String path, Headers headers) {
        throw new UnsupportedOperationException();
    }
}
