package shiver.me.timbers.http.servlet;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static shiver.me.timbers.data.random.RandomStrings.someString;

public class StreamsTest {

    private Streams streams;

    @Before
    public void setUp() {
        streams = new Streams();
    }

    @Test
    public void Can_write_to_a_stream() throws IOException {

        // Given
        final String input = someString();
        final ByteArrayOutputStream output = new ByteArrayOutputStream();

        // When
        streams.write(input, output);

        // Then
        assertThat(input, is(new String(output.toByteArray())));
    }

    @Test
    public void Can_read_from_a_stream() throws IOException {

        // Given
        final String expected = someString();


        // When
        final String actual = streams.readToString(new ByteArrayInputStream(expected.getBytes("UTF-8")));

        // Then
        assertThat(actual, is(expected));
    }
}