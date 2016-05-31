package shiver.me.timbers.http.mock;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import shiver.me.timbers.http.Headers;
import shiver.me.timbers.http.Request;
import shiver.me.timbers.http.mock.routers.HttpMockRequestRouter;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static shiver.me.timbers.data.random.RandomStrings.someString;

public class HttpMockRoutingRequestHandlerTest {

    private HttpMockHeaderFilter headerFilter;
    private HttpMockRequestRouter requestRouter;
    private HttpMockRoutingRequestHandler requestHandler;

    @Before
    public void setUp() {
        headerFilter = mock(HttpMockHeaderFilter.class);
        requestRouter = mock(HttpMockRequestRouter.class);
        requestHandler = new HttpMockRoutingRequestHandler(headerFilter, requestRouter);
    }

    @Test
    public void Can_handle_a_method_request() {

        final HttpMockHandler handler = mock(HttpMockHandler.class);
        final Request request = mock(Request.class);

        final String method = someString();
        final String path = someString();
        final Headers headers = mock(Headers.class);
        final HttpMockResponse expected = mock(HttpMockResponse.class);

        // Given
        given(request.getMethod()).willReturn(method);
        given(request.getPath()).willReturn(path);
        given(request.getHeaders()).willReturn(headers);
        given(headers.isEmpty()).willReturn(true);
        given(requestRouter.handlesMethod(method)).willReturn(true);
        given(requestRouter.route(handler, method, path)).willReturn(expected);

        // When
        final HttpMockResponse actual = requestHandler.handle(handler, request);

        // Then
        final InOrder order = inOrder(headerFilter, requestRouter);
        order.verify(headerFilter).filter(headers);
        order.verify(requestRouter).handlesMethod(method);
        order.verify(requestRouter).route(handler, method, path);
        verifyNoMoreInteractions(requestRouter);
        assertThat(actual, is(expected));
    }

    @Test
    public void Can_handle_a_method_request_with_headers() {

        final HttpMockHandler handler = mock(HttpMockHandler.class);
        final Request request = mock(Request.class);

        final String method = someString();
        final String path = someString();
        final Headers headers = mock(Headers.class);
        final HttpMockResponse expected = mock(HttpMockResponse.class);

        // Given
        given(request.getMethod()).willReturn(method);
        given(request.getPath()).willReturn(path);
        given(request.getHeaders()).willReturn(headers);
        given(headers.isEmpty()).willReturn(false);
        given(requestRouter.handlesMethod(method)).willReturn(true);
        given(requestRouter.route(handler, method, path, headers)).willReturn(expected);

        // When
        final HttpMockResponse actual = requestHandler.handle(handler, request);

        // Then
        final InOrder order = inOrder(headerFilter, requestRouter);
        order.verify(headerFilter).filter(headers);
        order.verify(requestRouter, times(2)).handlesMethod(method);
        order.verify(requestRouter).route(handler, method, path, headers);
        verifyNoMoreInteractions(requestRouter);
        assertThat(actual, is(expected));
    }

    @Test
    public void Cannot_handle_an_unknown_method_request() {

        final HttpMockHandler handler = mock(HttpMockHandler.class);
        final Request request = mock(Request.class);
        final String method = someString();

        // Given
        given(request.getMethod()).willReturn(method);
        given(requestRouter.handlesMethod(method)).willReturn(false);

        // When
        final HttpMockResponse actual = requestHandler.handle(handler, request);

        // Then
        verifyZeroInteractions(handler);
        assertThat(actual, nullValue());
    }
}