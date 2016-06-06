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

package shiver.me.timbers.http;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static shiver.me.timbers.data.random.RandomStrings.someString;

public class HeaderTest {

    @Test
    public void Head_names_are_always_stored_as_lower_case_so_that_the_case_is_ignored() {

        // Given
        final String name = someString();

        // When
        final String actual = new Header(name, someString()).getName();

        // Then
        assertThat(actual, equalTo(name.toLowerCase()));
    }

    @Test
    public void Header_has_equality() {
        EqualsVerifier.forClass(Header.class).usingGetClass().verify();
    }
}