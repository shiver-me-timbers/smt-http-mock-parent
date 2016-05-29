package shiver.me.timbers.http.mock;

/**
 * @author Karl Bennett
 */
class HttpMockHeadRequestHandler extends HttpMockMethodRequestHandler {

    HttpMockHeadRequestHandler() {
        super("HEAD");
    }

    @Override
    protected HttpMockResponse handleMethod(HttpMockHandler handler, String path) {
        return handler.head();
    }
}
