package shiver.me.timbers.http.mock;

import org.junit.Before;
import org.junit.Test;
import shiver.me.timbers.http.Headers;
import shiver.me.timbers.http.Request;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyZeroInteractions;
import static shiver.me.timbers.data.random.RandomStrings.someString;

public class HttpMockMethodRequestHandlerTest {

    private String method;
    private HttpMockHeaderFilter headerFilter;
    private HttpMockResponse response;

    @Before
    public void setUp() {
        method = someString();
        headerFilter = mock(HttpMockHeaderFilter.class);
        response = mock(HttpMockResponse.class);
    }

    @Test
    public void Can_handle_a_method_request() {

        final HttpMockHandler handler = mock(HttpMockHandler.class);
        final Request request = mock(Request.class);
        final String requestPath = someString();
        final Headers headers = mock(Headers.class);

        // Given
        given(request.getMethod()).willReturn(method);
        given(request.getPath()).willReturn(requestPath);
        given(request.getHeaders()).willReturn(headers);
        given(headers.isEmpty()).willReturn(true);

        // When
        final HttpMockResponse actual = new HttpMockMethodRequestHandler(method, headerFilter) {
            @Override
            protected HttpMockResponse handleMethod(HttpMockHandler handler, String path) {
                assertThat(path, is(requestPath));
                return response;
            }

            @Override
            protected HttpMockResponse handleMethod(HttpMockHandler handler, String path, Headers headers) {
                throw new AssertionError("This method should not be called.");
            }
        }.handle(handler, request);

        // Then
        then(headerFilter).should().filter(headers);
        assertThat(actual, is(response));
    }

    @Test
    public void Cannot_handle_an_unknown_method_request() {

        final HttpMockHandler handler = mock(HttpMockHandler.class);
        final Request request = mock(Request.class);

        // Given
        given(request.getMethod()).willReturn(someString());

        // When
        final HttpMockResponse actual = new HttpMockMethodRequestHandler(method, headerFilter) {
            @Override
            protected HttpMockResponse handleMethod(HttpMockHandler handler, String path) {
                throw new AssertionError("This method should not be called.");
            }

            @Override
            protected HttpMockResponse handleMethod(HttpMockHandler handler, String path, Headers headers) {
                throw new AssertionError("This method should not be called.");
            }
        }.handle(handler, request);

        // Then
        verifyZeroInteractions(handler);
        assertThat(actual, nullValue());
    }

    @Test
    public void Can_handle_a_method_request_with_headers() {

        final HttpMockHandler handler = mock(HttpMockHandler.class);
        final Request request = mock(Request.class);
        final String requestPath = someString();
        final Headers requestHeaders = mock(Headers.class);

        // Given
        given(request.getMethod()).willReturn(method);
        given(request.getPath()).willReturn(requestPath);
        given(request.getHeaders()).willReturn(requestHeaders);
        given(requestHeaders.isEmpty()).willReturn(false);

        // When
        final HttpMockResponse actual = new HttpMockMethodRequestHandler(method, headerFilter) {
            @Override
            protected HttpMockResponse handleMethod(HttpMockHandler handler, String path) {
                throw new AssertionError("This method should not be called.");
            }

            @Override
            protected HttpMockResponse handleMethod(HttpMockHandler handler, String path, Headers headers) {
                assertThat(path, is(requestPath));
                assertThat(headers, is(requestHeaders));
                return response;
            }
        }.handle(handler, request);

        // Then
        assertThat(actual, is(response));
    }
}