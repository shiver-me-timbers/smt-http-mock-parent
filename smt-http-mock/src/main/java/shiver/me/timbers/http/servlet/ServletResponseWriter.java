package shiver.me.timbers.http.servlet;

import shiver.me.timbers.http.Response;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Karl Bennett
 */
class ServletResponseWriter {

    private final Streams streams;

    ServletResponseWriter() {
        this(new Streams());
    }

    ServletResponseWriter(Streams streams) {
        this.streams = streams;
    }

    void write(HttpServletResponse servletResponse, Response response) {
        servletResponse.setStatus(response.getStatus());
        try {
            streams.write(response.getBodyAsString(), servletResponse.getOutputStream());
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
