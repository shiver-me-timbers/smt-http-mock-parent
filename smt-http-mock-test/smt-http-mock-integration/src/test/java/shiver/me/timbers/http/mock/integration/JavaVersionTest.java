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

package shiver.me.timbers.http.mock.integration;

import org.junit.Test;

import static java.lang.String.format;
import static org.hamcrest.Matchers.closeTo;
import static org.junit.Assert.assertThat;
import static shiver.me.timbers.data.random.RandomIntegers.somePositiveInteger;
import static shiver.me.timbers.http.mock.integration.JavaVersion.javaVersion;

public class JavaVersionTest {

    @Test
    public void Can_convert_a_version_to_a_double() {

        // Given
        final int major = somePositiveInteger();
        final int minor = somePositiveInteger();
        final String version = format("%d.%d.%d.%d", major, minor, somePositiveInteger(), somePositiveInteger());
        final double expected = Double.valueOf(format("%d.%d", major, minor));

        // When
        final double actual = javaVersion(version);

        // Then
        assertThat(actual, closeTo(expected, 0.0));
    }

    @Test
    public void Can_convert_the_java_version_to_a_double() {

        // When
        final double actual = javaVersion(System.getProperty("java.version"));

        // Then
        assertThat(actual, closeTo(1, 0.9));
    }
}