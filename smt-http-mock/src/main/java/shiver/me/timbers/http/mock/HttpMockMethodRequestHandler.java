package shiver.me.timbers.http.mock;

import shiver.me.timbers.http.Request;

/**
 * @author Karl Bennett
 */
abstract class HttpMockMethodRequestHandler implements HttpMockRequestHandler {

    private final String method;

    HttpMockMethodRequestHandler(String method) {
        this.method = method;
    }

    @Override
    public HttpMockResponse handle(HttpMockHandler handler, Request request) {
        if (method.equals(request.getMethod())) {
            return handleMethod(handler, request);
        }
        return null;
    }

    protected abstract HttpMockResponse handleMethod(HttpMockHandler handler, Request request);
}
