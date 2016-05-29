package shiver.me.timbers.http.mock;

import org.junit.Before;
import org.junit.Test;
import shiver.me.timbers.http.Request;
import shiver.me.timbers.http.Response;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class HttpMockServiceTest {

    private HttpMockService service;

    @Before
    public void setUp() {
        service = new HttpMockService();
    }

    @Test
    public void Can_get_the_http_mock_services_name() {

        // When
        final String actual = service.getName();

        // Then
        assertThat(actual, is(service.getClass().getSimpleName()));
    }

    @Test
    public void Can_get_the_http_mock_services_path() {

        // When
        final String actual = service.getPath();

        // Then
        assertThat(actual, is("/mock"));
    }

    @Test
    public void Can_call_the_http_mock_service() {

        final HttpMockHandler handler = mock(HttpMockHandler.class);

        final HttpMockResponse expected = mock(HttpMockResponse.class);

        // Given
        given(handler.get()).willReturn(expected);

        // When
        service.registerHandler(handler);
        final Response actual = service.call(mock(Request.class));

        // Then
        assertThat(actual, is((Response) expected));
    }

    @Test
    public void Can_get_the_http_mock_handler() {

        // Given
        final HttpMockHandler expected = mock(HttpMockHandler.class);

        // When
        final HttpMockHandler actual = service.registerHandler(expected);

        // Then
        assertThat(actual, is(expected));
    }
}