package shiver.me.timbers.http.mock;

import shiver.me.timbers.http.Headers;

/**
 * @author Karl Bennett
 */
class HttpMockHeadRequestHandler extends HttpMockMethodRequestHandler {

    HttpMockHeadRequestHandler(HttpMockHeaderFilter headerFilter) {
        super("HEAD", headerFilter);
    }

    @Override
    protected HttpMockResponse handleMethod(HttpMockHandler handler, String path) {
        return handler.head(path);
    }

    @Override
    protected HttpMockResponse handleMethod(HttpMockHandler handler, String path, Headers headers) {
        throw new UnsupportedOperationException();
    }
}
