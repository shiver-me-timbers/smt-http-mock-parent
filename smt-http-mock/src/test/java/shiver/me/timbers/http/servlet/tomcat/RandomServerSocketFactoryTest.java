package shiver.me.timbers.http.servlet.tomcat;

import org.junit.Test;

import java.io.IOException;
import java.net.ServerSocket;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

public class RandomServerSocketFactoryTest {

    @Test
    public void Can_create_a_random_socket() throws IOException {

        // When
        try (final ServerSocket actual = new RandomServerSocketFactory().create()) {

            // Then
            assertThat(actual, not(nullValue()));
            assertThat(actual.getLocalPort(), greaterThan(0));
        }
    }
}