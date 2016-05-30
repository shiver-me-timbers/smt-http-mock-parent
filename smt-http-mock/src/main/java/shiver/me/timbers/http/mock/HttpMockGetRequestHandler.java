package shiver.me.timbers.http.mock;

import shiver.me.timbers.http.Headers;

/**
 * @author Karl Bennett
 */
class HttpMockGetRequestHandler extends HttpMockMethodRequestHandler {

    HttpMockGetRequestHandler(HttpMockHeaderFilter headerFilter) {
        super("GET", headerFilter);
    }

    @Override
    protected HttpMockResponse handleMethod(HttpMockHandler handler, String path) {
        return handler.get(path);
    }

    @Override
    protected HttpMockResponse handleMethod(HttpMockHandler handler, String path, Headers headers) {
        throw new UnsupportedOperationException();
    }
}
