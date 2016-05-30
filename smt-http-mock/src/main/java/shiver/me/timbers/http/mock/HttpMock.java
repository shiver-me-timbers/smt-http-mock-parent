package shiver.me.timbers.http.mock;

import shiver.me.timbers.http.Header;
import shiver.me.timbers.http.Headers;

import java.util.HashSet;

import static java.util.Arrays.asList;

/**
 * @author Karl Bennett
 */
public class HttpMock {

    public static Header h(String name, String value) {
        return new Header(name, value);
    }

    public static Headers headers(Header... headers) {
        return new Headers(new HashSet<>(asList(headers)));
    }
}
