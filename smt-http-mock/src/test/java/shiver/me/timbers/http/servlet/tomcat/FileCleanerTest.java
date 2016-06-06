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

package shiver.me.timbers.http.servlet.tomcat;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static shiver.me.timbers.data.random.RandomStrings.someAlphaNumericString;

public class FileCleanerTest {

    private FileCleaner cleaner;

    @Before
    public void setUp() {
        cleaner = new FileCleaner();
    }

    @Test
    public void Can_delete_a_file() throws IOException {

        final String name = "target/" + someAlphaNumericString(8);
        final File file = new File(name);

        // Given
        file.createNewFile();

        // When
        cleaner.cleanUp(name);

        // Then
        assertThat(file.exists(), is(false));
    }

    @Test
    public void Can_delete_a_directory() throws IOException {

        final String name = "target/" + someAlphaNumericString(8);
        final File file = new File(name);

        // Given
        file.mkdir();

        // When
        cleaner.cleanUp(name);

        // Then
        assertThat(file.exists(), is(false));
    }

    @Test
    public void Can_delete_a_populated_directory() throws IOException {

        final String name = "target/" + someAlphaNumericString(8);
        final File file = new File(name);

        // Given
        file.mkdir();
        new File(file, someAlphaNumericString(8)).createNewFile();

        // When
        cleaner.cleanUp(name);

        // Then
        assertThat(file.exists(), is(false));
    }
}