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

import org.apache.catalina.Context;
import org.apache.tomcat.JarScanner;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static shiver.me.timbers.data.random.RandomStrings.someString;

public class Tomcat7ContextTest {

    private Context context;
    private CommonContext<JarScanner> tomcat8Context;

    @Before
    public void setUp() {
        context = mock(Context.class);
        tomcat8Context = new Tomcat7Context(context);
    }

    @Test
    public void Can_set_the_jar_scanner() {

        // Given
        final JarScanner jarScanner = mock(JarScanner.class);

        // When
        tomcat8Context.setJarScanner(jarScanner);

        // Then
        verify(context).setJarScanner(jarScanner);
    }

    @Test
    public void Can_get_the_path() {

        final String expected = someString();

        // Given
        given(context.getPath()).willReturn(expected);

        // When
        final String actual = tomcat8Context.getPath();

        // Then
        assertThat(actual, is(expected));
    }
}