package shiver.me.timbers.http;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Karl Bennett
 */
public class Headers {

    private final Set<Header> headers;

    public Headers(Set<Header> headers) {
        this.headers = new HashSet<>(headers);
    }

    public boolean isEmpty() {
        return headers.isEmpty();
    }

    public void remove(String name) {
        for (Header header : new HashSet<>(headers)) {
            if (name.equalsIgnoreCase(header.getName())) {
                headers.remove(header);
            }
        }
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        final Headers that = (Headers) object;

        return headers.equals(that.headers);

    }

    @Override
    public int hashCode() {
        return headers.hashCode();
    }

    @Override
    public String toString() {
        return "Headers{" +
            "headers=" + headers +
            '}';
    }
}
