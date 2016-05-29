package shiver.me.timbers.http.mock;

/**
 * @author Karl Bennett
 */
class HttpMockTraceRequestHandler extends HttpMockMethodRequestHandler {

    HttpMockTraceRequestHandler() {
        super("TRACE");
    }

    @Override
    protected HttpMockResponse handleMethod(HttpMockHandler handler, String path) {
        return handler.trace();
    }
}
