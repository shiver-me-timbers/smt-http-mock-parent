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

package shiver.me.timbers.http.mock;

import org.junit.Test;
import shiver.me.timbers.http.Header;
import shiver.me.timbers.http.Headers;

import java.util.HashSet;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static shiver.me.timbers.data.random.RandomStrings.someString;
import static shiver.me.timbers.http.mock.HttpMock.h;
import static shiver.me.timbers.http.mock.HttpMock.headers;

public class HttpMockTest {

    @Test
    public void Can_create_a_header() {

        // Given
        final String name = someString();
        final String value = someString();

        // When
        final Header actual = h(name, value);

        // Then
        assertThat(actual, equalTo(new Header(name, value)));
    }

    @Test
    public void Can_create_some_headers() {

        // Given
        final Header[] headers = {mock(Header.class), mock(Header.class), mock(Header.class)};

        // When
        final Headers actual = headers(headers);

        // Then
        assertThat(actual, equalTo(new Headers(new HashSet<>(asList(headers)))));
    }
}