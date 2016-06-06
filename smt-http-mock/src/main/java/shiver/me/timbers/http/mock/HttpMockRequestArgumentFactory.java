package shiver.me.timbers.http.mock;

import shiver.me.timbers.http.Headers;
import shiver.me.timbers.http.Request;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Karl Bennett
 */
class HttpMockRequestArgumentFactory {

    HttpMockArguments create(Request request) {
        final List<Class> types = new ArrayList<>();
        final List<Object> values = new ArrayList<>();

        types.add(String.class);
        values.add(request.getPath());

        if (request.hasHeaders()) {
            types.add(Headers.class);
            values.add(request.getHeaders());
        }
        if (request.hasBody()) {
            types.add(String.class);
            values.add(request.getBodyAsString());
        }

        return new HttpMockArguments(request.getMethod(), types, values);
    }
}
