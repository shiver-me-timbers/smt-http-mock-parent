package shiver.me.timbers.http.mock;

import shiver.me.timbers.http.Request;

/**
 * @author Karl Bennett
 */
class HttpMockReflectionHandlerRouter {

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
        final HttpMockArguments arguments = argumentFactory.create(requestFactory.create(request));

        try {
            final HttpMockResponse response = methodFinder.find(handler, arguments).invoke(handler);
            if (response != null) {
                return response;
            }

            return new HttpMockNotFoundResponse(arguments);
        } catch (NoSuchMethodException e) {
            return new HttpMockMethodNotAllowedResponse(arguments, e);
        }
    }
}
