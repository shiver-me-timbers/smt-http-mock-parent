package shiver.me.timbers.http.mock;

import org.junit.Test;

import java.lang.reflect.Method;

import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static shiver.me.timbers.matchers.Matchers.hasField;

public class HttpMockRequestMethodFinderTest {

    @Test
    public void Can_find_a_method() throws NoSuchMethodException {

        final TestInterface object = new TestInterface() {
            @Override
            public void test(Integer one, Double two, String three) {
                throw new UnsupportedOperationException();
            }
        };
        final HttpMockArguments arguments = mock(HttpMockArguments.class);

        final Class[] args = {Integer.class, Double.class, String.class};
        final Method test = object.getClass().getMethod("test", args);

        // Given
        given(arguments.getHttpMethod()).willReturn("TEST");
        given(arguments.toParameterTypes()).willReturn(args);

        // When
        final HttpMockMethodCall actual = new HttpMockRequestMethodFinder().find(object, arguments);

        // Then
        assertThat(actual, hasField("method", test));
        assertThat(actual, hasField("arguments", arguments));
    }

    private interface TestInterface {
        void test(Integer one, Double two, String three);
    }
}