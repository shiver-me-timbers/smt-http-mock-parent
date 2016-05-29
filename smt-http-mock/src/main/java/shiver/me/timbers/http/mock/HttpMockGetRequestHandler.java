package shiver.me.timbers.http.mock;

/**
 * @author Karl Bennett
 */
class HttpMockGetRequestHandler extends HttpMockMethodRequestHandler {

    HttpMockGetRequestHandler() {
        super("GET");
    }

    @Override
    protected HttpMockResponse handleMethod(HttpMockHandler handler, String path) {
        return handler.get(path);
    }
}
