package shiver.me.timbers.http.mock.routers;

import org.junit.Before;
import org.junit.Test;
import shiver.me.timbers.http.Headers;
import shiver.me.timbers.http.mock.HttpMockHandler;
import shiver.me.timbers.http.mock.HttpMockResponse;

import java.util.HashSet;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static shiver.me.timbers.data.random.RandomStrings.someString;
import static shiver.me.timbers.data.random.RandomThings.someThing;
import static shiver.me.timbers.http.Methods.METHODS;
import static shiver.me.timbers.http.Methods.TRACE;

public class HttpMockTraceRequestRouterTest {

    private HttpMockTraceRequestRouter router;

    @Before
    public void setUp() {
        router = new HttpMockTraceRequestRouter();
    }

    @Test
    public void Can_handle_a_trace_method() {

        // When
        final boolean actual = router.handlesMethod(TRACE);

        // Then
        assertThat(actual, is(true));
    }

    @Test
    public void Cannot_handle_any_other_method() {

        // Given
        final HashSet<String> otherMethods = new HashSet<>(METHODS);
        otherMethods.remove(TRACE);

        // When
        final boolean actual = router.handlesMethod(someThing(otherMethods.toArray(new String[otherMethods.size()])));

        // Then
        assertThat(actual, is(false));
    }

    @Test
    public void Can_handle_a_trace_request() {

        final HttpMockHandler handler = mock(HttpMockHandler.class);

        final String path = someString();

        final HttpMockResponse expected = mock(HttpMockResponse.class);

        // Given
        given(handler.trace(path)).willReturn(expected);

        // When
        final HttpMockResponse actual = router.route(handler, someString(), path);

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void Can_handle_a_trace_request_with_headers() {

        final HttpMockHandler handler = mock(HttpMockHandler.class);

        final String path = someString();
        final Headers headers = mock(Headers.class);

        final HttpMockResponse expected = mock(HttpMockResponse.class);

        // Given
        given(handler.trace(path, headers)).willReturn(expected);

        // When
        final HttpMockResponse actual = router.route(handler, someString(), path, headers);

        // Then
        assertThat(actual, is(expected));
    }
}