package shiver.me.timbers.http.mock;

import org.junit.Test;
import shiver.me.timbers.http.Request;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class HttpMockTraceRequestHandlerTest {

    @Test
    public void Can_handle_a_get_request() {

        final HttpMockHandler handler = mock(HttpMockHandler.class);
        final Request request = mock(Request.class);

        final HttpMockResponse expected = mock(HttpMockResponse.class);

        // Given
        given(request.getMethod()).willReturn("TRACE");
        given(handler.trace()).willReturn(expected);

        // When
        final HttpMockResponse actual = new HttpMockTraceRequestHandler().handle(handler, request);

        // Then
        assertThat(actual, is(expected));
    }
}