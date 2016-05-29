package shiver.me.timbers.http.servlet;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static shiver.me.timbers.data.random.RandomStrings.someString;

public class StreamWriterTest {

    @Test
    public void Can_write_to_a_stream() throws IOException {

        // Given
        final String input = someString();
        final ByteArrayOutputStream output = new ByteArrayOutputStream();

        // When
        new StreamWriter().write(input, output);

        // Then
        assertThat(input, is(new String(output.toByteArray())));
    }
}