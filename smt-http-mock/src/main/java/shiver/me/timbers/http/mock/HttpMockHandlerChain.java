package shiver.me.timbers.http.mock;

import shiver.me.timbers.http.Request;
import shiver.me.timbers.http.mock.routers.HttpMockDeleteRequestRouter;
import shiver.me.timbers.http.mock.routers.HttpMockGetRequestRouter;
import shiver.me.timbers.http.mock.routers.HttpMockHeadRequestRouter;
import shiver.me.timbers.http.mock.routers.HttpMockOptionsRequestRouter;
import shiver.me.timbers.http.mock.routers.HttpMockOtherRequestRouter;
import shiver.me.timbers.http.mock.routers.HttpMockPatchRequestRouter;
import shiver.me.timbers.http.mock.routers.HttpMockPostRequestRouter;
import shiver.me.timbers.http.mock.routers.HttpMockPutRequestRouter;
import shiver.me.timbers.http.mock.routers.HttpMockTraceRequestRouter;

import java.util.Arrays;
import java.util.List;

import static java.lang.String.format;
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
            Arrays.<HttpMockRequestHandler>asList(
                new HttpMockRoutingRequestHandler(headerFilter, new HttpMockGetRequestRouter()),
                new HttpMockRoutingRequestHandler(headerFilter, new HttpMockPostRequestRouter()),
                new HttpMockRoutingRequestHandler(headerFilter, new HttpMockPutRequestRouter()),
                new HttpMockRoutingRequestHandler(headerFilter, new HttpMockPatchRequestRouter()),
                new HttpMockRoutingRequestHandler(headerFilter, new HttpMockDeleteRequestRouter()),
                new HttpMockRoutingRequestHandler(headerFilter, new HttpMockOptionsRequestRouter()),
                new HttpMockRoutingRequestHandler(headerFilter, new HttpMockHeadRequestRouter()),
                new HttpMockRoutingRequestHandler(headerFilter, new HttpMockTraceRequestRouter()),
                new HttpMockRoutingRequestHandler(headerFilter, new HttpMockOtherRequestRouter())
            ),
            headerFilter
        );
    }

    HttpMockHandlerChain(List<HttpMockRequestHandler> requestHandlers, HttpMockHeaderFilter headerFilter) {
        this.requestHandlers = requestHandlers;
        this.headerFilter = headerFilter;
    }

    HttpMockResponse handle(HttpMockHandler handler, final Request request) {
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
                    "The %s request with path (%s) and headers (%s) has not been mocked.",
                    request.getMethod(),
                    request.getPath(),
                    request.getHeaders()
                );
            }
        };
    }

    void ignoreHeaders(String... names) {
        this.headerFilter.ignoredHeaders(names);
    }
}
