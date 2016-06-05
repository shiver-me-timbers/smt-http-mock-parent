package shiver.me.timbers.http.mock;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static java.lang.String.format;
import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static shiver.me.timbers.data.random.RandomStrings.someString;
import static shiver.me.timbers.http.StatusCodes.NOT_FOUND;

public class HttpMockNotFoundResponseTest {

    private HttpMockArguments arguments;
    private HttpMockNotFoundResponse response;

    @Before
    public void setUp() {
        arguments = mock(HttpMockArguments.class);
        response = new HttpMockNotFoundResponse(arguments);
    }

    @Test
    public void Can_get_the_method_not_allowed_responses_status() {

        // When
        final int actual = response.getStatus();

        // Then
        assertThat(actual, is(NOT_FOUND));
    }

    @Test
    public void Can_get_the_method_not_allowed_response_body_as_a_string() {

        final String httpMethod = someString();
        final List<Class> types = asList((Class) Integer.class, Double.class, String.class);

        // Given
        given(arguments.getHttpMethod()).willReturn(httpMethod);
        given(arguments.toParameterTypes()).willReturn(types.toArray(new Class[types.size()]));

        // When
        final String actual = response.getBodyAsString();

        // Then
        assertThat(actual, equalTo(format(
            "No result returned from mock method with the signature: %s(%s, %s, %s)",
            httpMethod.toLowerCase(),
            types.get(0).getSimpleName(), types.get(1).getSimpleName(), types.get(2).getSimpleName()
        )));
    }
}