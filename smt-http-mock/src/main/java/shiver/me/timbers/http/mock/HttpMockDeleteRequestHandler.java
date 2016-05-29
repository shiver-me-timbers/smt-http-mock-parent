package shiver.me.timbers.http.mock;

/**
 * @author Karl Bennett
 */
class HttpMockDeleteRequestHandler extends HttpMockMethodRequestHandler {

    HttpMockDeleteRequestHandler() {
        super("DELETE");
    }

    @Override
    protected HttpMockResponse handleMethod(HttpMockHandler handler, String path) {
        return handler.delete();
    }
}
