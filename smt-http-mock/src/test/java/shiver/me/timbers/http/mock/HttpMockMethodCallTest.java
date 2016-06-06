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

import java.lang.reflect.Method;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static shiver.me.timbers.data.random.RandomStrings.someString;

public class HttpMockMethodCallTest {

    @Test
    public void Can_invoke_the_handler_method() throws NoSuchMethodException {

        final HttpMockArguments arguments = mock(HttpMockArguments.class);
        final TestInterface object = mock(TestInterface.class);
        final Method method = object.getClass().getMethod("test", String.class, Headers.class);
        final Request request = mock(Request.class);

        final String path = someString();
        final Headers headers = mock(Headers.class);

        final HttpMockResponse expected = mock(HttpMockResponse.class);

        // Given
        given(request.getPath()).willReturn(path);
        given(request.getHeaders()).willReturn(headers);
        given(arguments.toParameters()).willReturn(new Object[]{path, headers});
        given(object.test(path, headers)).willReturn(expected);

        // When
        final HttpMockResponse actual = new HttpMockMethodCall(method, arguments).invoke(object);

        // Then
        assertThat(actual, is(expected));
    }

    private interface TestInterface {

        HttpMockResponse test(String path, Headers headers);
    }
}