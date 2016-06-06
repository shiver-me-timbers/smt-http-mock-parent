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

package shiver.me.timbers.http.servlet;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static shiver.me.timbers.data.random.RandomStrings.someString;

public class StreamsTest {

    private Streams streams;

    @Before
    public void setUp() {
        streams = new Streams();
    }

    @Test
    public void Can_write_to_a_stream() throws IOException {

        // Given
        final String input = someString();
        final ByteArrayOutputStream output = new ByteArrayOutputStream();

        // When
        streams.write(input, output);

        // Then
        assertThat(input, is(new String(output.toByteArray())));
    }

    @Test
    public void Can_read_from_a_stream() throws IOException {

        // Given
        final String expected = someString();


        // When
        final String actual = streams.readToString(new ByteArrayInputStream(expected.getBytes("UTF-8")));

        // Then
        assertThat(actual, is(expected));
    }
}