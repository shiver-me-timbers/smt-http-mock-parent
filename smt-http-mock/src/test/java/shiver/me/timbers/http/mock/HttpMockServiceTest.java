package shiver.me.timbers.http.mock;

import org.junit.Before;
import org.junit.Test;
import shiver.me.timbers.http.Request;
import shiver.me.timbers.http.Response;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static shiver.me.timbers.data.random.RandomShorts.someShort;

public class HttpMockServiceTest {

    private HttpMockService service;

    @Before
    public void setUp() {
        service = new HttpMockService();
    }

    @Test
    public void Can_get_the_services_name() {

        // When
        final String actual = service.getName();

        // Then
        assertThat(actual, is(service.getClass().getSimpleName()));
    }

    @Test
    public void Can_get_the_services_path() {

        // When
        final String actual = service.getPath();

        // Then
        assertThat(actual, is("/"));
    }

    @Test
    public void Can_record_a_get_request() {

        // Given
        final Request request = mock(Request.class);
        final int expected = someShort();

        // When
        for (int i = 0; i < expected; i++) {
            service.call(request);
        }
        final int actual = service.getNumberOfGetRequests();

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void Can_set_the_get_response() {

        // Given
        final MockHttpResponse expected = mock(MockHttpResponse.class);

        // When
        service.setGetResponse(expected);
        final Response actual = service.call(mock(Request.class));

        // Then
        assertThat(actual, is((Response) expected));
    }
}