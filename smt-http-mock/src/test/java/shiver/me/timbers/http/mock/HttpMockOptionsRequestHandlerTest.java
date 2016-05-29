package shiver.me.timbers.http.mock;

import org.junit.Test;
import shiver.me.timbers.http.Request;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static shiver.me.timbers.data.random.RandomStrings.someString;

public class HttpMockOptionsRequestHandlerTest {

    @Test
    public void Can_handle_a_options_request() {

        final HttpMockHandler handler = mock(HttpMockHandler.class);
        final Request request = mock(Request.class);

        final String path = someString();

        final HttpMockResponse expected = mock(HttpMockResponse.class);

        // Given
        given(request.getMethod()).willReturn("OPTIONS");
        given(request.getPath()).willReturn(path);
        given(handler.options(path)).willReturn(expected);

        // When
        final HttpMockResponse actual = new HttpMockOptionsRequestHandler().handle(handler, request);

        // Then
        assertThat(actual, is(expected));
    }
}