package shiver.me.timbers.http.servlet;

import org.junit.Test;
import shiver.me.timbers.http.Response;

import javax.servlet.http.HttpServletResponse;

import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

public class ServletResponseWriterTest {

    @Test
    public void Can_write_a_response() {

        // Given
        final HttpServletResponse servletResponse = mock(HttpServletResponse.class);
        final Response response = mock(Response.class);

        // When
        new ServletResponseWriter().write(servletResponse, response);

        // Then
        then(servletResponse).should().setStatus(response.getStatus());
    }
}