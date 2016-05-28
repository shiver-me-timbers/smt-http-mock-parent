package shiver.me.timbers.http.mock;

import org.junit.Test;

import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static shiver.me.timbers.matchers.Matchers.hasField;

public class HttpMockThenTest {

    @Test
    public void Can_verify_a_single_call() {

        // Given
        final HttpMockService service = mock(HttpMockService.class);

        // When
        final HttpMockVerify actual = new HttpMockThen(service).should();

        // Then
        assertThat(actual, hasField("service", service));
        assertThat(actual, hasField("times", 1));
    }
}