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

    void filter(Headers headers) {
        for (String name : names) {
            headers.remove(name);
        }
    }
}
