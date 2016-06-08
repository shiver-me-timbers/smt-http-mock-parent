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

import org.apache.catalina.connector.Connector;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static shiver.me.timbers.data.random.RandomBooleans.someBoolean;
import static shiver.me.timbers.data.random.RandomIntegers.someInteger;

public class Tomcat7ConnectorTest {

    private Connector connector;
    private Tomcat7Connector tomcat7Connector;

    @Before
    public void setUp() {
        connector = mock(Connector.class);
        tomcat7Connector = new Tomcat7Connector(connector);
    }

    @Test
    public void Can_allow_trace() {

        // Given
        final Boolean allowTrace = someBoolean();

        // When
        tomcat7Connector.setAllowTrace(allowTrace);

        // Then
        verify(connector).setAllowTrace(allowTrace);
    }

    @Test
    public void Can_get_the_local_port() {

        final Integer expected = someInteger();

        // Given
        given(connector.getLocalPort()).willReturn(expected);

        // When
        final int actual = tomcat7Connector.getLocalPort();

        // Then
        assertThat(actual, is(expected));
    }
}