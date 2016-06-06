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

import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static shiver.me.timbers.data.random.RandomDoubles.someDouble;
import static shiver.me.timbers.data.random.RandomIntegers.someInteger;
import static shiver.me.timbers.data.random.RandomStrings.someString;

public class HttpMockArgumentsTest {

    private String httpMethod;
    private List<Class> types;
    private List<Object> values;
    private HttpMockArguments arguments;

    @Before
    public void setUp() {
        httpMethod = someString();
        types = asList((Class) Integer.class, Double.class, String.class);
        values = asList((Object) someInteger(), someDouble(), someString());
        arguments = new HttpMockArguments(httpMethod, types, values);
    }

    @Test
    public void Can_get_the_arguments_http_method() {

        // When
        final String actual = arguments.getHttpMethod();

        // Then
        assertThat(actual, is(httpMethod));
    }

    @Test
    public void Can_convert_the_arguments_to_parameter_types() {

        // When
        final Class[] actual = arguments.toParameterTypes();

        // Then
        assertThat(actual, is(types.toArray(new Class[types.size()])));
    }

    @Test
    public void Can_convert_the_arguments_to_parameter_values() {

        // When
        final Object[] actual = arguments.toParameters();

        // Then
        assertThat(actual, is(values.toArray(new Object[values.size()])));
    }
}