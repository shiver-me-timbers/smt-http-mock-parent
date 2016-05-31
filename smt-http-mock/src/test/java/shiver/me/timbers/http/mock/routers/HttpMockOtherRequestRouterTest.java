package shiver.me.timbers.http.mock.routers;

import org.junit.Before;
import org.junit.Test;
import shiver.me.timbers.http.mock.HttpMockHandler;
import shiver.me.timbers.http.mock.HttpMockResponse;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static shiver.me.timbers.data.random.RandomStrings.someString;
import static shiver.me.timbers.data.random.RandomThings.someThing;
import static shiver.me.timbers.http.Methods.METHODS;

public class HttpMockOtherRequestRouterTest {

    private HttpMockOtherRequestRouter router;

    @Before
    public void setUp() {
        router = new HttpMockOtherRequestRouter();
    }

    @Test
    public void Can_handle_a_delete_method() {

        // When
        final boolean actual = router.handlesMethod(someString());

        // Then
        assertThat(actual, is(true));
    }

    @Test
    public void Cannot_handle_any_other_method() {

        // When
        final boolean actual = router.handlesMethod(someThing(METHODS.toArray(new String[METHODS.size()])));

        // Then
        assertThat(actual, is(false));
    }

    @Test
    public void Can_handle_a_delete_request() {

        final HttpMockHandler handler = mock(HttpMockHandler.class);
        final String method = someString();
        final String path = someString();

        final HttpMockResponse expected = mock(HttpMockResponse.class);

        // Given
        given(handler.request(method, path)).willReturn(expected);

        // When
        final HttpMockResponse actual = router.route(handler, method, path);

        // Then
        assertThat(actual, is(expected));
    }
}