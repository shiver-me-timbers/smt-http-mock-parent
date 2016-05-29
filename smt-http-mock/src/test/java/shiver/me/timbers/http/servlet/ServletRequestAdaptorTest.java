package shiver.me.timbers.http.servlet;

import org.junit.Test;

import javax.servlet.http.HttpServletRequest;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static shiver.me.timbers.data.random.RandomStrings.someString;

public class ServletRequestAdaptorTest {

    @Test
    public void Can_get_the_requests_method() {

        final HttpServletRequest request = mock(HttpServletRequest.class);

        final String expected = someString();

        // Given
        given(request.getMethod()).willReturn(expected);

        // When
        final String actual = new ServletRequestAdaptor(request).getMethod();

        // Then
        assertThat(actual, is(expected));
    }
}