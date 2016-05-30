package shiver.me.timbers.http.servlet;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

/**
 * @author Karl Bennett
 */
class StreamWriter {

    void write(String input, OutputStream output) throws IOException {
        IOUtils.write(input, output, Charset.defaultCharset());
        output.flush();
    }
}