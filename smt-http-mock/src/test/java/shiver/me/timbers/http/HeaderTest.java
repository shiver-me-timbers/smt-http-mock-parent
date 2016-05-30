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