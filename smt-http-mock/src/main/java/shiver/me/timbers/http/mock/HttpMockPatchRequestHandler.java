package shiver.me.timbers.http.mock;

import shiver.me.timbers.http.Headers;

/**
 * @author Karl Bennett
 */
class HttpMockPatchRequestHandler extends HttpMockMethodRequestHandler {

    HttpMockPatchRequestHandler(HttpMockHeaderFilter headerFilter) {
        super("PATCH", headerFilter);
    }

    @Override
    protected HttpMockResponse handleMethod(HttpMockHandler handler, String path) {
        return handler.patch(path);
    }

    @Override
    protected HttpMockResponse handleMethod(HttpMockHandler handler, String path, Headers headers) {
        throw new UnsupportedOperationException();
    }
}
