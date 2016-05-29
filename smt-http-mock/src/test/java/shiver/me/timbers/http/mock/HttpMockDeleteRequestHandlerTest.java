package shiver.me.timbers.http.mock;

import org.junit.Test;
import shiver.me.timbers.http.Request;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static shiver.me.timbers.data.random.RandomStrings.someString;

public class HttpMockDeleteRequestHandlerTest {

    @Test
    public void Can_handle_a_delete_request() {

        final HttpMockHandler handler = mock(HttpMockHandler.class);
        final Request request = mock(Request.class);

        final String path = someString();

        final HttpMockResponse expected = mock(HttpMockResponse.class);

        // Given
        given(request.getMethod()).willReturn("DELETE");
        given(request.getPath()).willReturn(path);
        given(handler.delete(path)).willReturn(expected);

        // When
        final HttpMockResponse actual = new HttpMockDeleteRequestHandler().handle(handler, request);

        // Then
        assertThat(actual, is(expected));
    }
}