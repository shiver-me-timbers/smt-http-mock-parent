package shiver.me.timbers.http.mock.router;

import org.junit.Test;
import shiver.me.timbers.http.mock.HttpMockHandler;
import shiver.me.timbers.http.mock.HttpMockResponse;
import shiver.me.timbers.http.mock.routers.HttpMockPatchRequestRouter;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static shiver.me.timbers.data.random.RandomStrings.someString;

public class HttpMockPatchRequestRouterTest {

    @Test
    public void Can_handle_a_patch_request() {

        final HttpMockHandler handler = mock(HttpMockHandler.class);

        final String path = someString();

        final HttpMockResponse expected = mock(HttpMockResponse.class);

        // Given
        given(handler.patch(path)).willReturn(expected);

        // When
        final HttpMockResponse actual = new HttpMockPatchRequestRouter().route(handler, someString(), path);

        // Then
        assertThat(actual, is(expected));
    }
}