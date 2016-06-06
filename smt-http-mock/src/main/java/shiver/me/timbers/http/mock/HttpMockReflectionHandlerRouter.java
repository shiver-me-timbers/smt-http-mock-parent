package shiver.me.timbers.http.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import shiver.me.timbers.http.Request;

/**
 * @author Karl Bennett
 */
class HttpMockReflectionHandlerRouter {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final HttpMockFilteredHeadersRequestFactory requestFactory;
    private final HttpMockRequestArgumentFactory argumentFactory;
    private final HttpMockRequestMethodFinder methodFinder;

    HttpMockReflectionHandlerRouter(HttpMockHeaderFilter headerFilter) {
        this(
            new HttpMockFilteredHeadersRequestFactory(headerFilter),
            new HttpMockRequestArgumentFactory(),
            new HttpMockRequestMethodFinder()
        );
    }

    HttpMockReflectionHandlerRouter(
        HttpMockFilteredHeadersRequestFactory requestFactory,
        HttpMockRequestArgumentFactory argumentFactory,
        HttpMockRequestMethodFinder methodFinder
    ) {
        this.requestFactory = requestFactory;
        this.argumentFactory = argumentFactory;
        this.methodFinder = methodFinder;
    }

    HttpMockResponse route(Object handler, Request request) {
        log.info(
            "Received handler {} and request ({} {}).",
            handler.getClass().getSimpleName(), request.getMethod(), request.getPath()
        );
        final HttpMockArguments arguments = argumentFactory.create(requestFactory.create(request));

        try {
            final HttpMockResponse response = methodFinder.find(handler, arguments).invoke(handler);
            if (response != null) {
                return response;
            }

            final HttpMockNotFoundResponse notFoundResponse = new HttpMockNotFoundResponse(arguments);
            log.error(notFoundResponse.getBodyAsString());
            return notFoundResponse;
        } catch (NoSuchMethodException e) {
            final HttpMockMethodNotAllowedResponse response = new HttpMockMethodNotAllowedResponse(arguments);
            log.error(response.getBodyAsString(), e);
            return response;
        }
    }
}
