package shiver.me.timbers.http.mock;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static shiver.me.timbers.data.random.RandomIntegers.someInteger;

public class MockHttpResponseTest {

    @Test
    public void Can_get_the_status() {

        // Given
        final Integer expected = someInteger();

        // When
        final int actual = new MockHttpResponse(expected).getStatus();

        // Then
        assertThat(actual, is(expected));
    }
}