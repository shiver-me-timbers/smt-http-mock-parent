package shiver.me.timbers.http.mock;

import org.junit.Before;
import org.junit.Test;
import shiver.me.timbers.http.Request;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyZeroInteractions;
import static shiver.me.timbers.data.random.RandomStrings.someString;
import static shiver.me.timbers.data.random.RandomThings.someThing;
import static shiver.me.timbers.http.Methods.METHODS;

public class HttpMockOtherRequestHandlerTest {

    private HttpMockOtherRequestHandler requestHandler;

    @Before
    public void setUp() {
        requestHandler = new HttpMockOtherRequestHandler();
    }

    @Test
    public void Can_handle_other_http_method() {

        final HttpMockHandler handler = mock(HttpMockHandler.class);
        final Request request = mock(Request.class);

        final String method = someString();
        final String path = someString();

        final HttpMockResponse expected = mock(HttpMockResponse.class);

        // Given
        given(request.getMethod()).willReturn(method);
        given(request.getPath()).willReturn(path);
        given(handler.request(method, path)).willReturn(expected);

        // When
        final HttpMockResponse actual = requestHandler.handle(handler, request);

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void Cannot_handle_standard_http_methods() {

        final HttpMockHandler handler = mock(HttpMockHandler.class);
        final Request request = mock(Request.class);

        // Given
        given(request.getMethod()).willReturn(someHttpMethod());

        // When
        final HttpMockResponse actual = requestHandler.handle(handler, request);

        // Then
        verifyZeroInteractions(handler);
        assertThat(actual, nullValue());
    }

    private static String someHttpMethod() {
        return someThing(METHODS.toArray(new String[METHODS.size()]));
    }
}