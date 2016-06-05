package shiver.me.timbers.http.mock;

import shiver.me.timbers.http.Headers;
import shiver.me.timbers.http.Request;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Karl Bennett
 */
class HttpMockRequestArgumentFactory {

    private final HttpMockHeaderFilter headerFilter;

    HttpMockRequestArgumentFactory(HttpMockHeaderFilter headerFilter) {
        this.headerFilter = headerFilter;
    }

    HttpMockArguments create(Request request) {
        final List<Class> types = new ArrayList<>();
        final List<Object> values = new ArrayList<>();

        types.add(String.class);
        values.add(request.getPath());

        final Headers headers = request.getHeaders();
        headerFilter.filter(headers);
        if (!headers.isEmpty()) {
            types.add(Headers.class);
            values.add(headers);
        }
        if (request.hasBody()) {
            types.add(String.class);
            values.add(request.getBodyAsString());
        }

        return new HttpMockArguments(request.getMethod(), types, values);
    }
}
