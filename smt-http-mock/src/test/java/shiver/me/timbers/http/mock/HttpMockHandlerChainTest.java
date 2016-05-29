package shiver.me.timbers.http.mock;

import org.junit.Before;
import org.junit.Test;
import shiver.me.timbers.http.Request;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class HttpMockHandlerChainTest {

    private static final int NOT_FOUND = 404;

    private HttpMockRequestHandler requestHandler;
    private HttpMockHandlerChain chain;

    @Before
    public void setUp() {
        requestHandler = mock(HttpMockRequestHandler.class);
        chain = new HttpMockHandlerChain(
            asList(mock(HttpMockRequestHandler.class), requestHandler, mock(HttpMockRequestHandler.class))
        );
    }

    @Test
    public void Can_handle_a_request() {

        final HttpMockHandler handler = mock(HttpMockHandler.class);
        final Request request = mock(Request.class);

        final HttpMockResponse expected = mock(HttpMockResponse.class);

        // Given
        given(requestHandler.handle(handler, request)).willReturn(expected);

        // When
        final HttpMockResponse actual = chain.handle(handler, request);

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void Can_fail_to_handle_a_request() {

        // Given
        final HttpMockHandler handler = mock(HttpMockHandler.class);
        final Request request = mock(Request.class);

        // When
        final HttpMockResponse actual = chain.handle(handler, request);

        // Then
        assertThat(actual.getStatus(), is(NOT_FOUND));
    }
}