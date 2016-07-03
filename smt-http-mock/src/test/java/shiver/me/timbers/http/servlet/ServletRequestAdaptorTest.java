/*
 * Copyright 2016 Karl Bennett
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package shiver.me.timbers.http.servlet;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import shiver.me.timbers.http.Headers;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static shiver.me.timbers.data.random.RandomBooleans.someBoolean;
import static shiver.me.timbers.data.random.RandomIntegers.somePositiveInteger;
import static shiver.me.timbers.data.random.RandomStrings.someString;

public class ServletRequestAdaptorTest {

    @Rule
    public final ExpectedException expectedException = ExpectedException.none();

    private HttpServletRequest request;
    private ServletHeadersExtractor headersExtractor;
    private Streams streams;
    private ServletRequestAdaptor adaptor;

    @Before
    public void setUp() {
        request = mock(HttpServletRequest.class);
        headersExtractor = mock(ServletHeadersExtractor.class);
        streams = mock(Streams.class);
        adaptor = new ServletRequestAdaptor(request, headersExtractor, streams);
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

        final String pathInfo = someString();
        final String queryString = someString();

        // Given
        given(request.getPathInfo()).willReturn(pathInfo);
        given(request.getQueryString()).willReturn(queryString);

        // When
        final String actual = adaptor.getPath();

        // Then
        assertThat(actual, is(pathInfo + "?" + queryString));
    }

    @Test
    public void Can_get_the_requests_path_with_no_query_string() {

        final String expected = someString();

        // Given
        given(request.getPathInfo()).willReturn(expected);
        given(request.getQueryString()).willReturn(null);

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

    @Test
    public void Can_get_the_requests_body_as_a_string() throws IOException {

        final ServletInputStream stream = mock(ServletInputStream.class);

        final String expected = someString();

        // Given
        given(request.getInputStream()).willReturn(stream);
        given(streams.readToString(stream)).willReturn(expected);

        // When
        final String actual = adaptor.getBodyAsString();

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void Can_fail_to_get_the_requests_body_as_a_string() throws IOException {

        final IOException exception = new IOException();

        // Given
        given(request.getInputStream()).willThrow(exception);
        expectedException.expect(IllegalStateException.class);
        expectedException.expectCause(is(exception));

        // When
        adaptor.getBodyAsString();
    }
}