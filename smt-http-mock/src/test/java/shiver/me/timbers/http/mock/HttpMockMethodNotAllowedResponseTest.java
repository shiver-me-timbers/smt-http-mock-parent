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
import static shiver.me.timbers.data.random.RandomDoubles.someDouble;
import static shiver.me.timbers.data.random.RandomIntegers.someInteger;
import static shiver.me.timbers.data.random.RandomStrings.someString;
import static shiver.me.timbers.http.StatusCodes.METHOD_NOT_ALLOWED;

public class HttpMockMethodNotAllowedResponseTest {

    private HttpMockArguments arguments;
    private HttpMockMethodNotAllowedResponse response;

    @Before
    public void setUp() {
        arguments = mock(HttpMockArguments.class);
        response = new HttpMockMethodNotAllowedResponse(arguments);
    }

    @Test
    public void Can_get_the_method_not_allowed_responses_status() {

        // When
        final int actual = response.getStatus();

        // Then
        assertThat(actual, is(METHOD_NOT_ALLOWED));
    }

    @Test
    public void Can_get_the_method_not_allowed_response_body_as_a_string() {

        final String httpMethod = someString();
        final List<Class> types = asList((Class) Integer.class, Double.class, String.class);
        final List<Object> parameters = asList((Object) someInteger(), someDouble(), someString());

        // Given
        given(arguments.getHttpMethod()).willReturn(httpMethod);
        given(arguments.toParameterTypes()).willReturn(types.toArray(new Class[types.size()]));
        given(arguments.toParameters()).willReturn(parameters.toArray(new Object[parameters.size()]));

        // When
        final String actual = response.getBodyAsString();

        // Then
        assertThat(actual, equalTo(format(
            "No http mock method found with the signature \"%s(%s, %s, %s)\" for request \"%s %s %s %s\".",
            httpMethod.toLowerCase(),
            types.get(0).getSimpleName(), types.get(1).getSimpleName(), types.get(2).getSimpleName(),
            httpMethod, parameters.get(0), parameters.get(1), parameters.get(2)
        )));
    }
}