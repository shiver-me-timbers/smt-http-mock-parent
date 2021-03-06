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

import org.junit.Before;
import org.junit.Test;
import shiver.me.timbers.http.Header;
import shiver.me.timbers.http.Headers;

import java.util.HashSet;
import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertThat;
import static shiver.me.timbers.data.random.RandomStrings.someString;
import static shiver.me.timbers.matchers.Matchers.hasField;

public class HttpMockHeaderFilterTest {

    private HttpMockHeaderFilter filter;

    @Before
    public void setUp() {
        filter = new HttpMockHeaderFilter();
    }

    @Test
    public void Can_filter_headers() {

        // Given
        final String name1 = someString();
        final String name2 = someString();
        final String name3 = someString();
        final Header header = new Header(name2, someString());
        final List<Header> headerList = asList(
            new Header(name1, someString()), header, new Header(name3, someString())
        );
        final Headers headers = new Headers(new HashSet<>(headerList));

        // When
        filter.ignoredHeaders(name1, name3);
        final Headers actual = filter.filter(headers);

        // Then
        assertThat(headers, hasField("headers", new HashSet<>(headerList)));
        assertThat(actual, contains(header));
    }

    @Test
    public void Can_filter_no_headers() {

        // Given
        final List<Header> headerList = asList(
            new Header(someString(), someString()),
            new Header(someString(), someString()),
            new Header(someString(), someString())
        );
        final Headers headers = new Headers(new HashSet<>(headerList));

        // When
        final Headers actual = filter.filter(headers);

        // Then
        assertThat(headers, hasField("headers", new HashSet<>(headerList)));
        assertThat(actual, containsInAnyOrder(headerList.toArray(new Header[headerList.size()])));
    }
}