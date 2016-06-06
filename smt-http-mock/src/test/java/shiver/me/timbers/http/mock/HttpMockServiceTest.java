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
import shiver.me.timbers.http.Request;
import shiver.me.timbers.http.Response;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static shiver.me.timbers.data.random.RandomStrings.someString;
import static shiver.me.timbers.data.random.RandomThings.someThing;

public class HttpMockServiceTest {

    private HttpMockService service;
    private HttpMockHeaderFilter headerFilter;
    private HttpMockReflectionHandlerRouter router;

    @Before
    public void setUp() {
        router = mock(HttpMockReflectionHandlerRouter.class);
        headerFilter = mock(HttpMockHeaderFilter.class);
        service = new HttpMockService(router, headerFilter);
    }

    @Test
    public void Can_get_the_http_mock_services_name() {

        // When
        final String actual = service.getName();

        // Then
        assertThat(actual, is(service.getClass().getSimpleName()));
    }

    @Test
    public void Can_get_the_http_mock_services_path() {

        // When
        final String actual = service.getPath();

        // Then
        assertThat(actual, is("/*"));
    }

    @Test
    public void Can_ignore_headers() {

        // Given
        final String[] names = {someString(), someString(), someString()};

        // When
        service.ignoreHeaders(names);

        // Then
        then(headerFilter).should().ignoredHeaders(names);
    }

    @Test
    public void Can_call_the_http_mock_service() {

        final HttpMockHandler handler = mock(HttpMockHandler.class);
        final Request request = mock(Request.class);

        final HttpMockResponse expected = mock(HttpMockResponse.class);

        // Given
        given(router.route(handler, request)).willReturn(expected);

        // When
        service.registerHandler(handler);
        final Response actual = service.call(request);

        // Then
        assertThat(actual, is((Response) expected));
    }

    @Test
    public void Can_register_the_http_mock_handler() {

        // Given
        final Object expected = someThing(Integer.class, Double.class, String.class);

        // When
        final Object actual = service.registerHandler(expected);

        // Then
        assertThat(actual, is(expected));
    }
}