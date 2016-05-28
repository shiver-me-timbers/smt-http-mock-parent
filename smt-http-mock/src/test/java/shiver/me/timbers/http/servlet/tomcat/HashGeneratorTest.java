package shiver.me.timbers.http.servlet.tomcat;

import org.junit.Test;

import java.util.HashSet;
import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

public class HashGeneratorTest {

    @Test
    public void Can_generate_a_random_hash() {

        // Given
        final Object object = new Object();
        final HashGenerator generator = new HashGenerator();

        // When
        final HashSet<Integer> actual = new HashSet<>(asList(
            generator.generate(object),
            generator.generate(object),
            generator.generate(object)
        ));

        // Then
        assertThat(actual, hasSize(1));
    }

    @Test
    public void Can_generate_unique_hashes() {

        // Given
        final HashGenerator generator = new HashGenerator();
        final List<Integer> hashes = asList(generator.generate(1), generator.generate(2), generator.generate(3));

        // When
        final HashSet<Integer> actual = new HashSet<>(hashes);

        // Then
        assertThat(actual, hasSize(hashes.size()));
    }
}