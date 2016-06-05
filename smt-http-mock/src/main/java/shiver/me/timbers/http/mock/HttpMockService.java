package shiver.me.timbers.http.mock;

import shiver.me.timbers.http.Request;
import shiver.me.timbers.http.Response;
import shiver.me.timbers.http.Service;

/**
 * @author Karl Bennett
 */
class HttpMockService implements Service {

    private final HttpMockReflectionHandlerRouter router;
    private final HttpMockHeaderFilter headerFilter;
    private Object handler;

    HttpMockService() {
        this(new HttpMockHeaderFilter());
    }

    private HttpMockService(HttpMockHeaderFilter headerFilter) {
        this(new HttpMockReflectionHandlerRouter(headerFilter), headerFilter);
    }

    HttpMockService(HttpMockReflectionHandlerRouter router, HttpMockHeaderFilter headerFilter) {
        this.router = router;
        this.headerFilter = headerFilter;
    }

    @Override
    public String getName() {
        return getClass().getSimpleName();
    }

    @Override
    public String getPath() {
        return "/*";
    }

    @Override
    public Response call(Request request) {
        return router.route(handler, request);
    }

    void ignoreHeaders(String... names) {
        headerFilter.ignoredHeaders(names);
    }

    @SuppressWarnings("unchecked")
    <T> T registerHandler(T handler) {
        return (T) (this.handler = handler);
    }
}
