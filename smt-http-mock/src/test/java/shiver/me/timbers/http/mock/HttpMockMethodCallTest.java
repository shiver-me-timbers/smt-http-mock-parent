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

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import shiver.me.timbers.http.Headers;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static shiver.me.timbers.data.random.RandomStrings.someString;

public class HttpMockMethodCallTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void Can_invoke_the_handler_method() throws NoSuchMethodException {

        final HttpMockArguments arguments = mock(HttpMockArguments.class);
        final TestInterface object = mock(TestInterface.class);
        final Method method = object.getClass().getMethod("test", String.class, Headers.class);

        final String path = someString();
        final Headers headers = mock(Headers.class);

        final HttpMockResponse expected = mock(HttpMockResponse.class);

        // Given
        given(arguments.toParameters()).willReturn(new Object[]{path, headers});
        given(object.test(path, headers)).willReturn(expected);

        // When
        final HttpMockResponse actual = new HttpMockMethodCall(method, arguments).invoke(object);

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void Can_fail_to_invoke_the_handler_method_because_of_no_access() throws NoSuchMethodException {

        final HttpMockArguments arguments = mock(HttpMockArguments.class);
        final PrivateTestClass object = new PrivateTestClass();
        final Method method = object.getClass().getDeclaredMethod("test");

        // Given
        given(arguments.toParameters()).willReturn(new Object[0]);
        expectedException.expect(IllegalStateException.class);
        expectedException.expectCause(Matchers.<Throwable>instanceOf(IllegalAccessException.class));

        // When
        new HttpMockMethodCall(method, arguments).invoke(object);
    }

    @Test
    public void Can_fail_to_invoke_the_handler_method_because_it_throws_an_exception() throws NoSuchMethodException {

        final HttpMockArguments arguments = mock(HttpMockArguments.class);
        final ThrowingTestClass object = new ThrowingTestClass();
        final Method method = object.getClass().getMethod("test");

        // Given
        given(arguments.toParameters()).willReturn(new Object[0]);
        expectedException.expect(IllegalStateException.class);
        expectedException.expectCause(Matchers.<Throwable>instanceOf(InvocationTargetException.class));

        // When
        new HttpMockMethodCall(method, arguments).invoke(object);
    }

    private interface TestInterface {

        HttpMockResponse test(String path, Headers headers);
    }

    private class PrivateTestClass {

        private void test() {
        }
    }

    private class ThrowingTestClass {

        public void test() {
            throw new RuntimeException();
        }
    }
}