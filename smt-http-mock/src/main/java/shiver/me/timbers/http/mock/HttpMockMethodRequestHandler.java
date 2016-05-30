package shiver.me.timbers.http.mock;

import shiver.me.timbers.http.Headers;
import shiver.me.timbers.http.Request;

/**
 * @author Karl Bennett
 */
abstract class HttpMockMethodRequestHandler implements HttpMockRequestHandler {

    private final String method;
    private final HttpMockHeaderFilter headerFilter;

    HttpMockMethodRequestHandler(String method, HttpMockHeaderFilter headerFilter) {
        this.method = method;
        this.headerFilter = headerFilter;
    }

    @Override
    public HttpMockResponse handle(HttpMockHandler handler, Request request) {
        final Headers headers = request.getHeaders();
        headerFilter.filter(headers);

        if (method.equals(request.getMethod()) && headers.isEmpty()) {
            return handleMethod(handler, request.getPath());
        }

        if (method.equals(request.getMethod()) && !headers.isEmpty()) {
            return handleMethod(handler, request.getPath(), headers);
        }
        return null;
    }

    protected abstract HttpMockResponse handleMethod(HttpMockHandler handler, String path);

    protected abstract HttpMockResponse handleMethod(HttpMockHandler handler, String path, Headers headers);
}
