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
import org.mockito.InOrder;
import shiver.me.timbers.http.Container;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static shiver.me.timbers.data.random.RandomIntegers.someInteger;
import static shiver.me.timbers.data.random.RandomStrings.someString;
import static shiver.me.timbers.data.random.RandomThings.someThing;

public class HttpMockContainerServerTest {

    private Container container;
    private HttpMockService service;
    private HttpMockContainerServer server;

    @Before
    public void setUp() {
        container = mock(Container.class);
        service = mock(HttpMockService.class);
        server = new HttpMockContainerServer(container, service);
    }

    @Test
    public void Creation_just_to_get_coverage() {
        new HttpMockContainerServer(mock(Container.class));
    }

    @Test
    public void Can_create_a_mock_http_server() {

        // When
        new HttpMockContainerServer(container, service);

        // Then
        final InOrder order = inOrder(container, service);
        order.verify(container).register(service);
        order.verify(container).start();
    }

    @Test
    public void Can_get_the_servers_port() {

        final Integer expected = someInteger();

        // Given
        given(container.getPort()).willReturn(expected);

        // When
        final int actual = server.getPort();

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void Can_ignore_headers() {

        // Given
        final String[] names = {someString(), someString(), someString()};

        // When
        server.ignoreHeaders(names);

        // Then
        then(service).should().ignoreHeaders(names);
    }

    @Test
    public void Can_create_a_mock_http_handler() {

        final Object expected = someThing(Integer.class, Double.class, String.class);

        // Given
        given(service.registerHandler(expected)).willReturn(expected);

        // When
        final Object actual = server.mock(expected);

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void Can_stop_a_mock_http_server() {

        // When
        server.stop();

        // Then
        then(container).should().stop();
    }
}