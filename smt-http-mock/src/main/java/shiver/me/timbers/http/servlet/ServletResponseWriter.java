package shiver.me.timbers.http.servlet;

import shiver.me.timbers.http.Response;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Karl Bennett
 */
class ServletResponseWriter {

    private final StreamWriter streamWriter;

    ServletResponseWriter() {
        this(new StreamWriter());
    }

    ServletResponseWriter(StreamWriter streamWriter) {
        this.streamWriter = streamWriter;
    }

    void write(HttpServletResponse servletResponse, Response response) {
        servletResponse.setStatus(response.getStatus());
        try {
            streamWriter.write(response.getBodyAsString(), servletResponse.getOutputStream());
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
