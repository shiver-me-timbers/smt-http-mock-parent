package shiver.me.timbers.http.mock;

import shiver.me.timbers.http.Request;

import static shiver.me.timbers.http.Methods.METHODS;

/**
 * @author Karl Bennett
 */
class HttpMockOtherRequestHandler implements HttpMockRequestHandler {

    private final HttpMockHeaderFilter headerFilter;

    HttpMockOtherRequestHandler(HttpMockHeaderFilter headerFilter) {
        this.headerFilter = headerFilter;
    }

    @Override
    public HttpMockResponse handle(HttpMockHandler handler, Request request) {
        if (isHttpMethod(request)) {
            return null;
        }

        headerFilter.filter(request.getHeaders());

        return handler.request(request.getMethod(), request.getPath());
    }

    private static boolean isHttpMethod(Request request) {
        return METHODS.contains(request.getMethod());
    }
}
