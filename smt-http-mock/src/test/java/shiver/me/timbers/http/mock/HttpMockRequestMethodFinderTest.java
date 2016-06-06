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

import java.lang.reflect.Method;

import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static shiver.me.timbers.matchers.Matchers.hasField;

public class HttpMockRequestMethodFinderTest {

    @Test
    public void Can_find_a_method() throws NoSuchMethodException {

        final TestInterface object = new TestInterface() {
            @Override
            public void test(Integer one, Double two, String three) {
                throw new UnsupportedOperationException();
            }
        };
        final HttpMockArguments arguments = mock(HttpMockArguments.class);

        final Class[] args = {Integer.class, Double.class, String.class};
        final Method test = object.getClass().getMethod("test", args);

        // Given
        given(arguments.getHttpMethod()).willReturn("TEST");
        given(arguments.toParameterTypes()).willReturn(args);

        // When
        final HttpMockMethodCall actual = new HttpMockRequestMethodFinder().find(object, arguments);

        // Then
        assertThat(actual, hasField("method", test));
        assertThat(actual, hasField("arguments", arguments));
    }

    private interface TestInterface {
        void test(Integer one, Double two, String three);
    }
}