package shiver.me.timbers.http.mock;

import org.junit.Before;
import org.junit.Test;
import shiver.me.timbers.http.Request;

import static java.lang.String.format;
import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static shiver.me.timbers.data.random.RandomStrings.someString;
import static shiver.me.timbers.http.StatusCodes.NOT_FOUND;

public class HttpMockHandlerChainTest {

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

        final HttpMockHandler handler = mock(HttpMockHandler.class);
        final Request request = mock(Request.class);

        final String method = someString();
        final String path = someString();

        // Given
        given(request.getMethod()).willReturn(method);
        given(request.getPath()).willReturn(path);

        // When
        final HttpMockResponse actual = chain.handle(handler, request);

        // Then
        assertThat(actual.getStatus(), is(NOT_FOUND));
        assertThat(actual.getBodyAsString(),
            is(format("The %s request with path (%s) has not been mocked.", method, path))
        );
    }
}