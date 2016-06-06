package shiver.me.timbers.http.mock;

import org.junit.Before;
import org.junit.Test;
import shiver.me.timbers.http.Request;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static shiver.me.timbers.data.random.RandomThings.someThing;
import static shiver.me.timbers.matchers.Matchers.hasField;

public class HttpMockReflectionHandlerRouterTest {

    private HttpMockRequestArgumentFactory argumentFactory;
    private HttpMockRequestMethodFinder methodFinder;
    private HttpMockReflectionHandlerRouter router;
    private HttpMockFilteredHeadersRequestFactory requestFactory;

    @Before
    public void setUp() {
        requestFactory = mock(HttpMockFilteredHeadersRequestFactory.class);
        argumentFactory = mock(HttpMockRequestArgumentFactory.class);
        methodFinder = mock(HttpMockRequestMethodFinder.class);
        router = new HttpMockReflectionHandlerRouter(requestFactory, argumentFactory, methodFinder);
    }

    @Test
    public void Can_handle_an_http_request() throws NoSuchMethodException {

        final Object handler = someThing(Integer.class, Double.class, String.class);
        final Request request = mock(Request.class);

        final Request mockRequest = mock(Request.class);
        final HttpMockArguments arguments = mock(HttpMockArguments.class);
        final HttpMockMethodCall method = mock(HttpMockMethodCall.class);

        final HttpMockResponse expected = mock(HttpMockResponse.class);

        // Given
        given(requestFactory.create(request)).willReturn(mockRequest);
        given(argumentFactory.create(mockRequest)).willReturn(arguments);
        given(methodFinder.find(handler, arguments)).willReturn(method);
        given(method.invoke(handler)).willReturn(expected);

        // When
        final HttpMockResponse actual = router.route(handler, request);

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void Can_handle_an_unknown_http_request() throws NoSuchMethodException {

        final Object handler = someThing(Integer.class, Double.class, String.class);
        final Request request = mock(Request.class);

        final Request mockRequest = mock(Request.class);
        final HttpMockArguments arguments = mock(HttpMockArguments.class);

        final NoSuchMethodException exception = new NoSuchMethodException();

        // Given
        given(requestFactory.create(request)).willReturn(mockRequest);
        given(argumentFactory.create(mockRequest)).willReturn(arguments);
        given(methodFinder.find(handler, arguments)).willThrow(exception);

        // When
        final HttpMockResponse actual = router.route(handler, request);

        // Then
        assertThat(actual, instanceOf(HttpMockMethodNotAllowedResponse.class));
        assertThat(actual, hasField("arguments", arguments));
        assertThat(actual, hasField("cause", exception));
    }

    @Test
    public void Can_handle_an_unmocked_http_request() throws NoSuchMethodException {

        final Object handler = someThing(Integer.class, Double.class, String.class);
        final Request request = mock(Request.class);

        final Request mockRequest = mock(Request.class);
        final HttpMockArguments arguments = mock(HttpMockArguments.class);
        final HttpMockMethodCall method = mock(HttpMockMethodCall.class);

        // Given
        given(requestFactory.create(request)).willReturn(mockRequest);
        given(argumentFactory.create(mockRequest)).willReturn(arguments);
        given(methodFinder.find(handler, arguments)).willReturn(method);
        given(method.invoke(handler)).willReturn(null);

        // When
        final HttpMockResponse actual = router.route(handler, request);

        // Then
        assertThat(actual, instanceOf(HttpMockNotFoundResponse.class));
        assertThat(actual, hasField("arguments", arguments));
    }
}