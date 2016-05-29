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

public class HttpMockMethodRequestHandlerTest {

    private String method;
    private HttpMockResponse response;

    @Before
    public void setUp() {
        method = someString();
        response = mock(HttpMockResponse.class);
    }

    @Test
    public void Can_handle_a_get_request() {

        final HttpMockHandler handler = mock(HttpMockHandler.class);
        final Request request = mock(Request.class);
        final String requestPath = someString();

        // Given
        given(request.getMethod()).willReturn(method);
        given(request.getPath()).willReturn(requestPath);

        // When
        final HttpMockResponse actual = new HttpMockMethodRequestHandler(method) {
            @Override
            protected HttpMockResponse handleMethod(HttpMockHandler handler, String path) {
                assertThat(path, is(requestPath));
                return response;
            }
        }.handle(handler, request);

        // Then
        assertThat(actual, is(response));
    }

    @Test
    public void Cannot_handle_a_non_get_request() {

        final HttpMockHandler handler = mock(HttpMockHandler.class);
        final Request request = mock(Request.class);

        // Given
        given(request.getMethod()).willReturn(someString());

        // When
        final HttpMockResponse actual = new HttpMockMethodRequestHandler(method) {
            @Override
            protected HttpMockResponse handleMethod(HttpMockHandler handler, String path) {
                throw new AssertionError("This method should not be called.");
            }
        }.handle(handler, request);

        // Then
        verifyZeroInteractions(handler);
        assertThat(actual, nullValue());
    }
}