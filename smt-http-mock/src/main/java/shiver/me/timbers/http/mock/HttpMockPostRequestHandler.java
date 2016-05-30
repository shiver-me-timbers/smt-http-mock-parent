package shiver.me.timbers.http.mock;

import shiver.me.timbers.http.Headers;

/**
 * @author Karl Bennett
 */
class HttpMockPostRequestHandler extends HttpMockMethodRequestHandler {

    HttpMockPostRequestHandler(HttpMockHeaderFilter headerFilter) {
        super("POST", headerFilter);
    }

    @Override
    protected HttpMockResponse handleMethod(HttpMockHandler handler, String path) {
        return handler.post(path);
    }

    @Override
    protected HttpMockResponse handleMethod(HttpMockHandler handler, String path, Headers headers) {
        throw new UnsupportedOperationException();
    }
}
