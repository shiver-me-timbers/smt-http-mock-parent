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
import shiver.me.timbers.http.Headers;
import shiver.me.timbers.http.Request;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static shiver.me.timbers.data.random.RandomBooleans.someBoolean;
import static shiver.me.timbers.data.random.RandomStrings.someString;

public class HttpMockFilteredHeadersRequestFactoryTest {

    @Test
    public void Can_create_a_request() {

        final HttpMockHeaderFilter headerFilter = mock(HttpMockHeaderFilter.class);
        final Request request = mock(Request.class);

        final Headers headers = mock(Headers.class);
        final Headers filteredHeaders = mock(Headers.class);
        final String method = someString();
        final String path = someString();
        final Boolean hasBody = someBoolean();
        final String body = someString();

        // Given
        given(request.getHeaders()).willReturn(headers);
        given(headerFilter.filter(headers)).willReturn(filteredHeaders);
        given(filteredHeaders.isEmpty()).willReturn(true, false);
        given(request.getMethod()).willReturn(method);
        given(request.getPath()).willReturn(path);
        given(request.hasBody()).willReturn(hasBody);
        given(request.getBodyAsString()).willReturn(body);

        // When
        final Request actual = new HttpMockFilteredHeadersRequestFactory(headerFilter).create(request);

        // Then
        assertThat(actual.getMethod(), is(method));
        assertThat(actual.getPath(), is(path));
        assertThat(actual.hasHeaders(), is(false));
        assertThat(actual.hasHeaders(), is(true));
        assertThat(actual.getHeaders(), is(filteredHeaders));
        assertThat(actual.hasBody(), is(hasBody));
        assertThat(actual.getBodyAsString(), is(body));
    }
}