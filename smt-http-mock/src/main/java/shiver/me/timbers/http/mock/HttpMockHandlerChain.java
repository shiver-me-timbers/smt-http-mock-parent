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
    private final HttpMockHeaderFilter headerFilter;

    HttpMockHandlerChain() {
        this(new HttpMockHeaderFilter());
    }

    private HttpMockHandlerChain(HttpMockHeaderFilter headerFilter) {
        this(
            asList(
                new HttpMockGetRequestHandler(headerFilter),
                new HttpMockPostRequestHandler(headerFilter),
                new HttpMockPutRequestHandler(headerFilter),
                new HttpMockPatchRequestHandler(headerFilter),
                new HttpMockDeleteRequestHandler(headerFilter),
                new HttpMockOptionsRequestHandler(headerFilter),
                new HttpMockHeadRequestHandler(headerFilter),
                new HttpMockTraceRequestHandler(headerFilter),
                new HttpMockOtherRequestHandler(headerFilter)
            ),
            headerFilter
        );
    }

    HttpMockHandlerChain(List<HttpMockRequestHandler> requestHandlers, HttpMockHeaderFilter headerFilter) {
        this.requestHandlers = requestHandlers;
        this.headerFilter = headerFilter;
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

    void ignoreHeaders(String... names) {
        this.headerFilter.ignoredHeaders(names);
    }
}
