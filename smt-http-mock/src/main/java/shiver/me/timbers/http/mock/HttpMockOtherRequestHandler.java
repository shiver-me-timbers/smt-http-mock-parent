package shiver.me.timbers.http.mock;

import shiver.me.timbers.http.Request;

/**
 * @author Karl Bennett
 */
class HttpMockOtherRequestHandler implements HttpMockRequestHandler {

    @Override
    public HttpMockResponse handle(HttpMockHandler handler, Request request) {
        return handler.request(request.getMethod());
    }
}
