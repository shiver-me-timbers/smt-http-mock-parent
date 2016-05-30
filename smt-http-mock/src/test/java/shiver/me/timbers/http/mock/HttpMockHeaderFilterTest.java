package shiver.me.timbers.http.mock;

import org.junit.Test;
import shiver.me.timbers.http.Headers;

import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static shiver.me.timbers.data.random.RandomStrings.someString;

public class HttpMockHeaderFilterTest {

    @Test
    public void Can_filter_headers() {

        // Given
        final Headers headers = mock(Headers.class);
        final String name1 = someString();
        final String name2 = someString();
        final String name3 = someString();

        // When
        final HttpMockHeaderFilter filter = new HttpMockHeaderFilter();
        filter.ignoredHeaders(name1, name2, name3);
        filter.filter(headers);

        // Then
        then(headers).should().remove(name1);
        then(headers).should().remove(name2);
        then(headers).should().remove(name3);
    }
}