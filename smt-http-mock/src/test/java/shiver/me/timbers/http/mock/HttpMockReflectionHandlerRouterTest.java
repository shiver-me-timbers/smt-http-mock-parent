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

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static shiver.me.timbers.data.random.RandomStrings.someString;
import static shiver.me.timbers.data.random.RandomThings.someThing;
import static shiver.me.timbers.matchers.Matchers.hasField;

public class HttpMockReflectionHandlerRouterTest {

    private HttpMockRequestArgumentFactory argumentFactory;
    private HttpMockRequestMethodFinder methodFinder;
    private HttpMockReflectionHandlerRouter router;
    private HttpMockFilteredHeadersRequestFactory requestFactory;

    @Before
    public void setUp() {
        requestFactory = mock(HttpMockFilteredHeadersRequestFactory.class);
        argumentFactory = mock(HttpMockRequestArgumentFactory.class);
        methodFinder = mock(HttpMockRequestMethodFinder.class);
        router = new HttpMockReflectionHandlerRouter(requestFactory, argumentFactory, methodFinder);
    }

    @Test
    public void Creation_just_to_get_coverage() {
        new HttpMockReflectionHandlerRouter(mock(HttpMockHeaderFilter.class));
    }

    @Test
    public void Can_handle_an_http_request() throws NoSuchMethodException {

        final Object handler = someThing(Integer.class, Double.class, String.class);
        final Request request = mock(Request.class);

        final Request mockRequest = mock(Request.class);
        final HttpMockArguments arguments = mock(HttpMockArguments.class);
        final HttpMockMethodCall method = mock(HttpMockMethodCall.class);

        final HttpMockResponse expected = mock(HttpMockResponse.class);

        // Given
        given(requestFactory.create(request)).willReturn(mockRequest);
        given(argumentFactory.create(mockRequest)).willReturn(arguments);
        given(methodFinder.find(handler, arguments)).willReturn(method);
        given(method.invoke(handler)).willReturn(expected);

        // When
        final HttpMockResponse actual = router.route(handler, request);

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void Can_handle_an_unknown_http_request() throws NoSuchMethodException {

        final Object handler = someThing(Integer.class, Double.class, String.class);
        final Request request = mock(Request.class);

        final Request mockRequest = mock(Request.class);
        final HttpMockArguments arguments = mock(HttpMockArguments.class);

        final NoSuchMethodException exception = new NoSuchMethodException();

        // Given
        given(requestFactory.create(request)).willReturn(mockRequest);
        given(argumentFactory.create(mockRequest)).willReturn(arguments);
        given(methodFinder.find(handler, arguments)).willThrow(exception);
        // These argument mocks are required to stop the logging from exploding.
        given(arguments.getHttpMethod()).willReturn(someString());
        given(arguments.toParameterTypes()).willReturn(new Class[0]);
        given(arguments.toParameters()).willReturn(new Object[0]);

        // When
        final HttpMockResponse actual = router.route(handler, request);

        // Then
        assertThat(actual, instanceOf(HttpMockMethodNotAllowedResponse.class));
        assertThat(actual, hasField("arguments", arguments));
    }

    @Test
    public void Can_handle_an_unmocked_http_request() throws NoSuchMethodException {

        final Object handler = someThing(Integer.class, Double.class, String.class);
        final Request request = mock(Request.class);

        final Request mockRequest = mock(Request.class);
        final HttpMockArguments arguments = mock(HttpMockArguments.class);
        final HttpMockMethodCall method = mock(HttpMockMethodCall.class);

        // Given
        given(requestFactory.create(request)).willReturn(mockRequest);
        given(argumentFactory.create(mockRequest)).willReturn(arguments);
        given(methodFinder.find(handler, arguments)).willReturn(method);
        given(method.invoke(handler)).willReturn(null);
        // These argument mocks are required to stop the logging from exploding.
        given(arguments.getHttpMethod()).willReturn(someString());
        given(arguments.toParameterTypes()).willReturn(new Class[0]);
        given(arguments.toParameters()).willReturn(new Object[0]);

        // When
        final HttpMockResponse actual = router.route(handler, request);

        // Then
        assertThat(actual, instanceOf(HttpMockNotFoundResponse.class));
        assertThat(actual, hasField("arguments", arguments));
    }
}