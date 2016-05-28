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
import static shiver.me.timbers.matchers.Matchers.hasField;

public class HttpMockServerTest {

    private Container container;
    private HttpMockService service;
    private HttpMockServer server;

    @Before
    public void setUp() {
        container = mock(Container.class);
        service = mock(HttpMockService.class);
        server = new HttpMockServer(container, service);
    }

    @Test
    public void Can_create_a_server() {

        // Given
        final Container container = mock(Container.class);
        final HttpMockService service = mock(HttpMockService.class);

        // When
        new HttpMockServer(container, service);

        // Then
        final InOrder order = inOrder(container, service);
        order.verify(container).register(service);
        order.verify(container).start();
    }

    @Test
    public void Can_get_the_port() {

        final int expected = someInteger();

        // Given
        given(container.getPort()).willReturn(expected);

        // When
        final int actual = server.getPort();

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void Can_stub_a_get_request() {

        // When
        final HttpGetStubbing actual = server.get();

        // Then
        assertThat(actual, hasField("service", service));
    }

    @Test
    public void Can_stop_the_server() {

        // When
        server.stop();

        // Then
        then(container).should().stop();
    }
}