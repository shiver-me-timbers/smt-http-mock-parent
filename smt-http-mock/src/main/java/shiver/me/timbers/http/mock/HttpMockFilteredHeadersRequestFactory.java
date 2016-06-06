package shiver.me.timbers.http.mock;

import shiver.me.timbers.http.Headers;
import shiver.me.timbers.http.Request;

/**
 * @author Karl Bennett
 */
class HttpMockFilteredHeadersRequestFactory {

    private final HttpMockHeaderFilter headerFilter;

    HttpMockFilteredHeadersRequestFactory(HttpMockHeaderFilter headerFilter) {
        this.headerFilter = headerFilter;
    }

    public Request create(final Request request) {
        final Headers headers = headerFilter.filter(request.getHeaders());
        return new Request() {
            @Override
            public String getMethod() {
                return request.getMethod();
            }

            @Override
            public String getPath() {
                return request.getPath();
            }

            @Override
            public boolean hasHeaders() {
                return !headers.isEmpty();
            }

            @Override
            public Headers getHeaders() {
                return headers;
            }

            @Override
            public boolean hasBody() {
                return request.hasBody();
            }

            @Override
            public String getBodyAsString() {
                return request.getBodyAsString();
            }
        };
    }
}
