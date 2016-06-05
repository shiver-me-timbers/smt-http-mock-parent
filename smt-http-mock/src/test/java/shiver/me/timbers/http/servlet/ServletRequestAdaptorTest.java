package shiver.me.timbers.http.servlet;

import org.junit.Before;
import org.junit.Test;
import shiver.me.timbers.http.Headers;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static shiver.me.timbers.data.random.RandomBooleans.someBoolean;
import static shiver.me.timbers.data.random.RandomIntegers.somePositiveInteger;
import static shiver.me.timbers.data.random.RandomStrings.someString;

public class ServletRequestAdaptorTest {

    private HttpServletRequest request;
    private ServletHeadersExtractor headersExtractor;
    private ServletRequestAdaptor adaptor;

    @Before
    public void setUp() {
        request = mock(HttpServletRequest.class);
        headersExtractor = mock(ServletHeadersExtractor.class);
        adaptor = new ServletRequestAdaptor(request, headersExtractor);
    }

    @Test
    public void Can_get_the_requests_method() {

        final String expected = someString();

        // Given
        given(request.getMethod()).willReturn(expected);

        // When
        final String actual = adaptor.getMethod();

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void Can_get_the_requests_path() {

        final String expected = someString();

        // Given
        given(request.getPathInfo()).willReturn(expected);

        // When
        final String actual = adaptor.getPath();

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void Can_check_that_the_requests_has_headers() {

        @SuppressWarnings("unchecked")
        final Enumeration<String> names = mock(Enumeration.class);

        final Boolean expected = someBoolean();

        // Given
        given(request.getHeaderNames()).willReturn(names);
        given(names.hasMoreElements()).willReturn(expected);

        // When
        final boolean actual = adaptor.hasHeaders();

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void Can_get_the_requests_headers() {

        final Headers expected = mock(Headers.class);

        // Given
        given(headersExtractor.extract(request)).willReturn(expected);

        // When
        final Headers actual = adaptor.getHeaders();

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void Can_check_that_the_requests_has_a_body() {

        // Given
        given(request.getContentLength()).willReturn(somePositiveInteger());

        // When
        final boolean actual = adaptor.hasBody();

        // Then
        assertThat(actual, is(true));
    }

    @Test
    public void Can_check_that_the_requests_does_not_have_a_body() {

        // Given
        given(request.getContentLength()).willReturn(0);

        // When
        final boolean actual = adaptor.hasBody();

        // Then
        assertThat(actual, is(false));
    }
}