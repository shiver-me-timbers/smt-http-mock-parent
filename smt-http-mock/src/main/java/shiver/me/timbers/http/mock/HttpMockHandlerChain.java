package shiver.me.timbers.http.mock;

import shiver.me.timbers.http.Request;

import java.util.List;

import static java.util.Arrays.asList;

/**
 * @author Karl Bennett
 */
class HttpMockHandlerChain {

    private static final int NOT_FOUND = 404;

    private final List<HttpMockRequestHandler> requestHandlers;

    HttpMockHandlerChain() {
        this(asList(
            new HttpMockGetRequestHandler(),
            new HttpMockPostRequestHandler(),
            new HttpMockPutRequestHandler(),
            new HttpMockPatchRequestHandler(),
            new HttpMockDeleteRequestHandler(),
            new HttpMockOptionsRequestHandler(),
            new HttpMockHeadRequestHandler(),
            new HttpMockTraceRequestHandler(),
            new HttpMockOtherRequestHandler()
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
