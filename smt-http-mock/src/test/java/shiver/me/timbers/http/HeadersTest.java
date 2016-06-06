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

package shiver.me.timbers.http;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.singleton;
import static nl.jqno.equalsverifier.Warning.NULL_FIELDS;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static shiver.me.timbers.data.random.RandomStrings.someString;
import static shiver.me.timbers.matchers.Matchers.hasField;

public class HeadersTest {

    @Test
    public void Can_check_that_headers_are_empty() {

        // When
        final boolean actual = new Headers(Collections.<Header>emptySet()).isEmpty();

        // Then
        assertThat(actual, is(true));
    }

    @Test
    public void Can_check_that_headers_are_not_empty() {

        // When
        final boolean actual = new Headers(singleton(mock(Header.class))).isEmpty();

        // Then
        assertThat(actual, is(false));
    }

    @Test
    public void Can_remove_a_header() {

        final Header header1 = mock(Header.class);
        final Header header2 = mock(Header.class);
        final Header header3 = mock(Header.class);
        final List<Header> headerList = asList(header1, header2, header3);
        final Headers headers = new Headers(new HashSet<>(headerList));
        final String name = someString();

        // Given
        given(header2.getName()).willReturn(name.toLowerCase());

        // When
        final Headers actual = new Headers(headers);
        actual.remove(name);

        // Then
        assertThat(headers, hasField("headers", new HashSet<>(headerList)));
        assertThat(actual, hasField("headers", new HashSet<>(asList(header1, header3))));
    }

    @Test
    public void Headers_have_equality() {
        EqualsVerifier.forClass(Headers.class).suppress(NULL_FIELDS).usingGetClass().verify();
    }
}