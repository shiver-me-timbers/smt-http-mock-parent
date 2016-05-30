package shiver.me.timbers.http.mock;

import org.junit.Before;
import org.junit.Test;
import shiver.me.timbers.http.Headers;
import shiver.me.timbers.http.Request;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static shiver.me.timbers.data.random.RandomStrings.someString;

public class HttpMockGetRequestHandlerTest {

    private HttpMockGetRequestHandler handler;

    @Before
    public void setUp() {
        handler = new HttpMockGetRequestHandler(mock(HttpMockHeaderFilter.class));
    }

    @Test
    public void Can_handle_a_get_request() {

        final HttpMockHandler handler = mock(HttpMockHandler.class);
        final Request request = mock(Request.class);

        final String path = someString();
        final Headers headers = mock(Headers.class);

        final HttpMockResponse expected = mock(HttpMockResponse.class);

        // Given
        given(request.getMethod()).willReturn("GET");
        given(request.getPath()).willReturn(path);
        given(request.getHeaders()).willReturn(headers);
        given(headers.isEmpty()).willReturn(true);
        given(handler.get(path)).willReturn(expected);

        // When
        final HttpMockResponse actual = this.handler.handle(handler, request);

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void Can_handle_a_get_request_with_headers() {

        final HttpMockHandler handler = mock(HttpMockHandler.class);
        final Request request = mock(Request.class);

        final String path = someString();
        final Headers headers = mock(Headers.class);

        final HttpMockResponse expected = mock(HttpMockResponse.class);

        // Given
        given(request.getMethod()).willReturn("GET");
        given(request.getPath()).willReturn(path);
        given(request.getHeaders()).willReturn(headers);
        given(headers.isEmpty()).willReturn(false);
        given(handler.get(path, headers)).willReturn(expected);

        // When
        final HttpMockResponse actual = this.handler.handle(handler, request);

        // Then
        assertThat(actual, is(expected));
    }
}