package shiver.me.timbers.http.mock;

import shiver.me.timbers.http.Headers;
import shiver.me.timbers.http.Request;
import shiver.me.timbers.http.mock.routers.HttpMockRequestRouter;

/**
 * @author Karl Bennett
 */
class HttpMockRoutingRequestHandler implements HttpMockRequestHandler {

    private final HttpMockHeaderFilter headerFilter;
    private final HttpMockRequestRouter requestRouter;

    HttpMockRoutingRequestHandler(HttpMockHeaderFilter headerFilter, HttpMockRequestRouter requestRouter) {
        this.headerFilter = headerFilter;
        this.requestRouter = requestRouter;
    }

    @Override
    public HttpMockResponse handle(HttpMockHandler handler, Request request) {
        final Headers headers = request.getHeaders();
        headerFilter.filter(headers);

        final String requestMethod = request.getMethod();
        if (requestRouter.handlesMethod(requestMethod) && headers.isEmpty()) {
            return requestRouter.route(handler, requestMethod, request.getPath());
        }
        if (requestRouter.handlesMethod(requestMethod) && !headers.isEmpty()) {
            return requestRouter.route(handler, requestMethod, request.getPath(), headers);
        }

        return null;
    }
}
