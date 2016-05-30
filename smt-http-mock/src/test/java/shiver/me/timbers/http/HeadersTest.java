package shiver.me.timbers.http;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

import java.util.Collections;
import java.util.HashSet;

import static java.util.Arrays.asList;
import static nl.jqno.equalsverifier.Warning.NULL_FIELDS;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static shiver.me.timbers.data.random.RandomStrings.someString;
import static shiver.me.timbers.matchers.Matchers.hasField;

public class HeadersTest {

    @Test
    public void Can_check_that_headers_are_empty() {

        // When
        final boolean actual = new Headers(Collections.<Header>emptySet()).isEmpty();

        // Then
        assertThat(actual, is(true));
    }

    @Test
    public void Can_check_that_headers_are_not_empty() {

        // When
        final boolean actual = new Headers(new HashSet<>(asList(mock(Header.class)))).isEmpty();

        // Then
        assertThat(actual, is(false));
    }

    @Test
    public void Can_remove_a_header() {

        final Header header1 = mock(Header.class);
        final Header header2 = mock(Header.class);
        final Header header3 = mock(Header.class);
        final HashSet<Header> headerSet = new HashSet<>(asList(header1, header2, header3));
        final String name = someString();

        // Given
        given(header2.getName()).willReturn(name.toLowerCase());

        // When
        final Headers headers = new Headers(headerSet);
        headers.remove(name);

        // Then
        assertThat(headerSet, equalTo(new HashSet<>(asList(header1, header2, header3))));
        assertThat(headers, hasField("headers", new HashSet<>(asList(header1, header3))));
    }

    @Test
    public void Headers_have_equality() {
        EqualsVerifier.forClass(Headers.class).suppress(NULL_FIELDS).usingGetClass().verify();
    }
}