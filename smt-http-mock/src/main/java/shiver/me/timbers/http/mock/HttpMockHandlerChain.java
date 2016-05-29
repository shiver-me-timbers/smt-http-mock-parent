package shiver.me.timbers.http.mock;

import shiver.me.timbers.http.Request;

import java.util.Arrays;
import java.util.List;

/**
 * @author Karl Bennett
 */
class HttpMockHandlerChain {

    private static final int NOT_FOUND = 404;

    private final List<HttpMockRequestHandler> requestHandlers;

    HttpMockHandlerChain() {
        this(Arrays.<HttpMockRequestHandler>asList(
            new HttpMockGetRequestHandler(),
            new HttpMockPostRequestHandler(),
            new HttpMockPutRequestHandler(),
            new HttpMockDeleteRequestHandler(),
            new HttpMockOptionsRequestHandler(),
            new HttpMockHeadRequestHandler(),
            new HttpMockTraceRequestHandler()
        ));
    }

    HttpMockHandlerChain(List<HttpMockRequestHandler> requestHandlers) {
        this.requestHandlers = requestHandlers;
    }

    public HttpMockResponse handle(HttpMockHandler handler, Request request) {
        for (HttpMockRequestHandler requestHandler : requestHandlers) {
            final HttpMockResponse response = requestHandler.handle(handler, request);
            if (response != null) {
                return response;
            }
        }

        return new HttpMockResponse() {
            @Override
            public int getStatus() {
                return NOT_FOUND;
            }
        };
    }
}
