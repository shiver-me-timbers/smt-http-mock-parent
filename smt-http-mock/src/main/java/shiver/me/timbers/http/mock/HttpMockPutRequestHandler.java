package shiver.me.timbers.http.mock;

/**
 * @author Karl Bennett
 */
class HttpMockPutRequestHandler extends HttpMockMethodRequestHandler {

    HttpMockPutRequestHandler() {
        super("PUT");
    }

    @Override
    protected HttpMockResponse handleMethod(HttpMockHandler handler, String path) {
        return handler.put(path);
    }
}
