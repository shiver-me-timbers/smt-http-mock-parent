package shiver.me.timbers.http.mock;

import org.junit.Test;
import shiver.me.timbers.http.Header;
import shiver.me.timbers.http.Headers;

import java.util.HashSet;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static shiver.me.timbers.data.random.RandomStrings.someString;
import static shiver.me.timbers.http.mock.HttpMock.h;
import static shiver.me.timbers.http.mock.HttpMock.headers;

public class HttpMockTest {

    @Test
    public void Can_create_a_header() {

        // Given
        final String name = someString();
        final String value = someString();

        // When
        final Header actual = h(name, value);

        // Then
        assertThat(actual, equalTo(new Header(name, value)));
    }

    @Test
    public void Can_create_some_headers() {

        // Given
        final Header[] headers = {mock(Header.class), mock(Header.class), mock(Header.class)};

        // When
        final Headers actual = headers(headers);

        // Then
        assertThat(actual, equalTo(new Headers(new HashSet<>(asList(headers)))));
    }
}