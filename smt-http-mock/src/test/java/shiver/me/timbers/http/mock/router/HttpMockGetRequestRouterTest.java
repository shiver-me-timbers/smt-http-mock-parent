package shiver.me.timbers.http.mock.router;

import org.junit.Before;
import org.junit.Test;
import shiver.me.timbers.http.Headers;
import shiver.me.timbers.http.mock.HttpMockHandler;
import shiver.me.timbers.http.mock.HttpMockResponse;
import shiver.me.timbers.http.mock.routers.HttpMockGetRequestRouter;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static shiver.me.timbers.data.random.RandomStrings.someString;

public class HttpMockGetRequestRouterTest {

    private HttpMockGetRequestRouter router;

    @Before
    public void setUp() {
        router = new HttpMockGetRequestRouter();
    }

    @Test
    public void Can_handle_a_get_request() {

        final HttpMockHandler handler = mock(HttpMockHandler.class);

        final String path = someString();

        final HttpMockResponse expected = mock(HttpMockResponse.class);

        // Given
        given(handler.get(path)).willReturn(expected);

        // When
        final HttpMockResponse actual = router.route(handler, someString(), path);

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void Can_handle_a_get_request_with_headers() {

        final HttpMockHandler handler = mock(HttpMockHandler.class);

        final String path = someString();
        final Headers headers = mock(Headers.class);

        final HttpMockResponse expected = mock(HttpMockResponse.class);

        // Given
        given(handler.get(path, headers)).willReturn(expected);

        // When
        final HttpMockResponse actual = router.route(handler, someString(), path, headers);

        // Then
        assertThat(actual, is(expected));
    }
}