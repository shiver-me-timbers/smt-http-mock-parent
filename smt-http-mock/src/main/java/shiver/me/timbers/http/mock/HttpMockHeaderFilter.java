package shiver.me.timbers.http.mock;

import shiver.me.timbers.http.Headers;

/**
 * @author Karl Bennett
 */
class HttpMockHeaderFilter {

    private String[] names;

    void ignoredHeaders(String... names) {
        this.names = names;
    }

    Headers filter(Headers headers) {
        final Headers filteredHeaders = new Headers(headers);
        for (String name : names) {
            filteredHeaders.remove(name);
        }
        return filteredHeaders;
    }
}
