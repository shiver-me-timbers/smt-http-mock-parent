package shiver.me.timbers.http.servlet;

import org.junit.Test;
import shiver.me.timbers.http.Header;
import shiver.me.timbers.http.Headers;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashSet;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static shiver.me.timbers.data.random.RandomStrings.someString;

public class ServletHeadersExtractorTest {

    @Test
    @SuppressWarnings("unchecked")
    public void Can_extract_the_servlet_request_headers() {

        final HttpServletRequest request = mock(HttpServletRequest.class);

        final Enumeration<String> headerNames = mock(Enumeration.class);
        final String name1 = someString();
        final String name2 = someString();
        final String name3 = someString();
        final Enumeration<String> values1 = mock(Enumeration.class);
        final Enumeration<String> values2 = mock(Enumeration.class);
        final Enumeration<String> values3 = mock(Enumeration.class);
        final String value1 = someString();
        final String value2_1 = someString();
        final String value2_2 = someString();
        final String value3_1 = someString();
        final String value3_2 = someString();
        final String value3_3 = someString();

        // Given
        given(request.getHeaderNames()).willReturn(headerNames);
        given(headerNames.hasMoreElements()).willReturn(true, true, true, false);
        given(headerNames.nextElement()).willReturn(name1, name2, name3);
        given(request.getHeaders(name1)).willReturn(values1);
        given(request.getHeaders(name2)).willReturn(values2);
        given(request.getHeaders(name3)).willReturn(values3);
        given(values1.hasMoreElements()).willReturn(true, false);
        given(values1.nextElement()).willReturn(value1);
        given(values2.hasMoreElements()).willReturn(true, true, false);
        given(values2.nextElement()).willReturn(value2_1, value2_2);
        given(values3.hasMoreElements()).willReturn(true, true, true, false);
        given(values3.nextElement()).willReturn(value3_1, value3_2, value3_3);

        // When
        final Headers actual = new ServletHeadersExtractor().extract(request);

        // Then
        assertThat(actual, equalTo(new Headers(
            new HashSet<>(asList(
                new Header(name1, value1),
                new Header(name2, value2_1),
                new Header(name2, value2_2),
                new Header(name3, value3_1),
                new Header(name3, value3_2),
                new Header(name3, value3_3)
            ))
        )));
    }
}