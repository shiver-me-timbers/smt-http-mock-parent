package shiver.me.timbers.http.mock;

import shiver.me.timbers.http.Request;

import java.util.List;

import static java.lang.String.format;
import static java.util.Arrays.asList;
import static shiver.me.timbers.http.StatusCodes.NOT_FOUND;

/**
 * @author Karl Bennett
 */
class HttpMockHandlerChain {

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

    public HttpMockResponse handle(HttpMockHandler handler, final Request request) {
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

            @Override
            public String getBodyAsString() {
                return format(
                    "The %s request with path (%s) has not been mocked.",
                    request.getMethod(),
                    request.getPath()
                );
            }
        };
    }
}
