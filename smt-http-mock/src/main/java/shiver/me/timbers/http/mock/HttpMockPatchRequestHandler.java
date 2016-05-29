package shiver.me.timbers.http.mock;

/**
 * @author Karl Bennett
 */
class HttpMockPatchRequestHandler extends HttpMockMethodRequestHandler {

    HttpMockPatchRequestHandler() {
        super("PATCH");
    }

    @Override
    protected HttpMockResponse handleMethod(HttpMockHandler handler, String path) {
        return handler.patch(path);
    }
}
