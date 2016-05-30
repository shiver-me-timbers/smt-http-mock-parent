package shiver.me.timbers.http.servlet;

import shiver.me.timbers.http.Header;
import shiver.me.timbers.http.Headers;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Karl Bennett
 */
class ServletHeadersExtractor {

    Headers extract(HttpServletRequest request) {
        final Set<Header> headers = new HashSet<>();

        final Enumeration<String> names = request.getHeaderNames();
        while (names.hasMoreElements()) {
            final String name = names.nextElement();
            addHeaders(headers, name, request.getHeaders(name));
        }

        return new Headers(headers);
    }

    private static void addHeaders(Set<Header> headers, String name, Enumeration<String> values) {
        while (values.hasMoreElements()) {
            headers.add(new Header(name, values.nextElement()));
        }
    }
}
