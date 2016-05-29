package shiver.me.timbers.http.mock;

/**
 * @author Karl Bennett
 */
class HttpMockOptionsRequestHandler extends HttpMockMethodRequestHandler {

    HttpMockOptionsRequestHandler() {
        super("OPTIONS");
    }

    @Override
    protected HttpMockResponse handleMethod(HttpMockHandler handler, String path) {
        return handler.options(path);
    }
}
