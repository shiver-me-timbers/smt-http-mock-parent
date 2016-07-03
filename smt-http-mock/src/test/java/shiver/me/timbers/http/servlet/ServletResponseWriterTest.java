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
import shiver.me.timbers.http.Response;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static shiver.me.timbers.data.random.RandomStrings.someString;

public class ServletResponseWriterTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private ServletResponseWriter writer;
    private HeadersWriter headersWriter;
    private Streams streams;

    @Before
    public void setUp() {
        streams = mock(Streams.class);
        headersWriter = mock(HeadersWriter.class);
        writer = new ServletResponseWriter(headersWriter, streams);
    }

    @Test
    public void Can_write_a_response() throws IOException {

        final HttpServletResponse servletResponse = mock(HttpServletResponse.class);
        final Response response = mock(Response.class);

        final Headers headers = mock(Headers.class);
        final String body = someString();
        final ServletOutputStream outputStream = mock(ServletOutputStream.class);

        // Given
        given(response.getHeaders()).willReturn(headers);
        given(response.getBodyAsString()).willReturn(body);
        given(servletResponse.getOutputStream()).willReturn(outputStream);

        // When
        writer.write(servletResponse, response);

        // Then
        then(servletResponse).should().setStatus(response.getStatus());
        then(headersWriter).should().write(headers, servletResponse);
        then(streams).should().write(body, outputStream);
    }

    @Test
    public void Can_fail_write_a_response() throws IOException {

        final HttpServletResponse servletResponse = mock(HttpServletResponse.class);
        final Response response = mock(Response.class);

        final Headers headers = mock(Headers.class);
        final String body = someString();
        final IOException exception = new IOException();

        // Given
        given(response.getHeaders()).willReturn(headers);
        given(response.getBodyAsString()).willReturn(body);
        given(servletResponse.getOutputStream()).willThrow(exception);
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectCause(is(exception));

        // When
        writer.write(servletResponse, response);
    }
}