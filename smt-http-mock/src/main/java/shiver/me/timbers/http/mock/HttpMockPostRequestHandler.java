package shiver.me.timbers.http.mock;

/**
 * @author Karl Bennett
 */
class HttpMockPostRequestHandler extends HttpMockMethodRequestHandler {

    HttpMockPostRequestHandler() {
        super("POST");
    }

    @Override
    protected HttpMockResponse handleMethod(HttpMockHandler handler, String path) {
        return handler.post();
    }
}
