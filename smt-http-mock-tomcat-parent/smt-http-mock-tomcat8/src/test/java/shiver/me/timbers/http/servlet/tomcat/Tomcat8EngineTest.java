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

import org.apache.catalina.Engine;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static shiver.me.timbers.data.random.RandomStrings.someString;

public class Tomcat8EngineTest {

    private Engine engine;
    private Tomcat8Engine tomcat8Engine;

    @Before
    public void setUp() {
        engine = mock(Engine.class);
        tomcat8Engine = new Tomcat8Engine(engine);
    }

    @Test
    public void Can_get_the_name() {

        final String expected = someString();

        // Given
        given(engine.getName()).willReturn(expected);

        // When
        final String actual = tomcat8Engine.getName();

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void Can_set_the_name() {

        // Given
        final String name = someString();

        // When
        tomcat8Engine.setName(name);

        // Then
        verify(engine).setName(name);
    }
}