package shiver.me.timbers.http.servlet.tomcat;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;
import java.net.ServerSocket;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static shiver.me.timbers.data.random.RandomIntegers.someInteger;

public class PortGeneratorTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void Can_generate_a_supplied_port() {

        // Given
        final Integer expected = someInteger();

        // When
        final int actual = new PortGenerator(expected).generatePort();

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void Can_generate_a_random_port() throws IOException {

        final RandomServerSocketFactory socketFactory = mock(RandomServerSocketFactory.class);

        final ServerSocket socket = mock(ServerSocket.class);
        final int expected = someInteger();

        // Given
        given(socketFactory.create()).willReturn(socket);
        given(socket.getLocalPort()).willReturn(expected);

        // When
        final int actual = new PortGenerator(0, socketFactory).generatePort();

        // Then
        then(socket).should().close();
        assertThat(actual, is(expected));
    }

    @Test
    public void Can_fail_to_generate_a_random_port() throws IOException {

        final RandomServerSocketFactory socketFactory = mock(RandomServerSocketFactory.class);

        final IOException exception = new IOException();

        // Given
        given(socketFactory.create()).willThrow(exception);
        expectedException.expect(IllegalStateException.class);
        expectedException.expectCause(is(exception));

        // When
        new PortGenerator(0, socketFactory).generatePort();
    }
}