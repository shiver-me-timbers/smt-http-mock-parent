package shiver.me.timbers.http.mock;

import org.junit.Test;
import shiver.me.timbers.http.Headers;
import shiver.me.timbers.http.Request;

import java.lang.reflect.Method;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static shiver.me.timbers.data.random.RandomStrings.someString;

public class HttpMockMethodCallTest {

    @Test
    public void Can_invoke_the_handler_method() throws NoSuchMethodException {

        final HttpMockArguments arguments = mock(HttpMockArguments.class);
        final TestInterface object = mock(TestInterface.class);
        final Method method = object.getClass().getMethod("test", String.class, Headers.class);
        final Request request = mock(Request.class);

        final String path = someString();
        final Headers headers = mock(Headers.class);

        final HttpMockResponse expected = mock(HttpMockResponse.class);

        // Given
        given(request.getPath()).willReturn(path);
        given(request.getHeaders()).willReturn(headers);
        given(arguments.toParameters()).willReturn(new Object[]{path, headers});
        given(object.test(path, headers)).willReturn(expected);

        // When
        final HttpMockResponse actual = new HttpMockMethodCall(method, arguments).invoke(object);

        // Then
        assertThat(actual, is(expected));
    }

    private interface TestInterface {

        HttpMockResponse test(String path, Headers headers);
    }
}