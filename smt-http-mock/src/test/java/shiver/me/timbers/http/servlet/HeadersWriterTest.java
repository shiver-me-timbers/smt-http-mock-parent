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

import org.junit.Test;
import shiver.me.timbers.http.Header;
import shiver.me.timbers.http.Headers;

import javax.servlet.http.HttpServletResponse;

import static java.util.Arrays.asList;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyZeroInteractions;
import static shiver.me.timbers.data.random.RandomStrings.someString;

public class HeadersWriterTest {

    @Test
    public void Can_write_headers() {

        final Headers headers = mock(Headers.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);

        final Header header1 = mock(Header.class);
        final Header header2 = mock(Header.class);
        final Header header3 = mock(Header.class);
        final String name1 = someString();
        final String name2 = someString();
        final String name3 = someString();
        final String value1 = someString();
        final String value2 = someString();
        final String value3 = someString();

        // Given
        given(headers.iterator()).willReturn(asList(header1, header2, header3).iterator());
        given(header1.getName()).willReturn(name1);
        given(header1.getValue()).willReturn(value1);
        given(header2.getName()).willReturn(name2);
        given(header2.getValue()).willReturn(value2);
        given(header3.getName()).willReturn(name3);
        given(header3.getValue()).willReturn(value3);

        // When
        new HeadersWriter().write(headers, response);

        // Then
        then(response).should().addHeader(name1, value1);
        then(response).should().addHeader(name2, value2);
        then(response).should().addHeader(name3, value3);
    }

    @Test
    public void Can_write_null_headers() {

        // Given
        final HttpServletResponse response = mock(HttpServletResponse.class);

        // When
        new HeadersWriter().write(null, response);

        // Then
        verifyZeroInteractions(response);
    }
}