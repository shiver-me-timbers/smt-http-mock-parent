package shiver.me.timbers.http.servlet;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import shiver.me.timbers.http.Response;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static shiver.me.timbers.data.random.RandomStrings.someString;

public class ServletResponseWriterTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private ServletResponseWriter writer;
    private Streams streams;

    @Before
    public void setUp() {
        streams = mock(Streams.class);
        writer = new ServletResponseWriter(streams);
    }

    @Test
    public void Can_write_a_response() throws IOException {

        final HttpServletResponse servletResponse = mock(HttpServletResponse.class);
        final Response response = mock(Response.class);

        final String body = someString();
        final ServletOutputStream outputStream = mock(ServletOutputStream.class);

        // Given
        given(response.getBodyAsString()).willReturn(body);
        given(servletResponse.getOutputStream()).willReturn(outputStream);

        // When
        writer.write(servletResponse, response);

        // Then
        then(servletResponse).should().setStatus(response.getStatus());
        then(streams).should().write(body, outputStream);
    }

    @Test
    public void Can_fail_write_a_response() throws IOException {

        final HttpServletResponse servletResponse = mock(HttpServletResponse.class);
        final Response response = mock(Response.class);

        final String body = someString();
        final IOException exception = new IOException();

        // Given
        given(response.getBodyAsString()).willReturn(body);
        given(servletResponse.getOutputStream()).willThrow(exception);
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectCause(is(exception));

        // When
        writer.write(servletResponse, response);
    }
}