package shiver.me.timbers.http.mock.integration;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import shiver.me.timbers.http.mock.HttpMockHandler;
import shiver.me.timbers.http.mock.HttpMockResponse;
import shiver.me.timbers.http.mock.HttpMockServer;

import javax.ws.rs.core.Response;

import static javax.ws.rs.client.Entity.text;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static shiver.me.timbers.http.mock.integration.HttpClients.createClient;

public class ITHttpRequest {

    private static final int OK = 200;

    private HttpMockServer http;

    @Before
    public void setUp() {
        http = new HttpMockServer();
    }

    @After
    public void tearDown() {
        http.stop();
    }

    @Test
    public void Can_mock_an_http_get_request() {

        final HttpMockHandler handler = http.mock(mock(HttpMockHandler.class));
        final HttpMockResponse response = mock(HttpMockResponse.class);

        // Given
        given(handler.get()).willReturn(response);
        given(response.getStatus()).willReturn(OK);

        // When
        final Response actual = createClient(http).get();

        // Then
        assertThat(actual.getStatus(), is(OK));
    }

    @Test
    public void Can_mock_an_http_post_request() {

        final HttpMockHandler handler = http.mock(mock(HttpMockHandler.class));
        final HttpMockResponse response = mock(HttpMockResponse.class);

        // Given
        given(handler.post()).willReturn(response);
        given(response.getStatus()).willReturn(OK);

        // When
        final Response actual = createClient(http).post(text(null), Response.class);

        // Then
        assertThat(actual.getStatus(), is(OK));
    }

    @Test
    public void Can_mock_an_http_put_request() {

        final HttpMockHandler handler = http.mock(mock(HttpMockHandler.class));
        final HttpMockResponse response = mock(HttpMockResponse.class);

        // Given
        given(handler.put()).willReturn(response);
        given(response.getStatus()).willReturn(OK);

        // When
        final Response actual = createClient(http).put(text(""));

        // Then
        assertThat(actual.getStatus(), is(OK));
    }
}

