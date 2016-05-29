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
import static shiver.me.timbers.data.random.RandomStrings.buildSomeString;
import static shiver.me.timbers.data.random.RandomStrings.someAlphaString;
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

        final String path = somePath();

        final HttpMockHandler handler = http.mock(mock(HttpMockHandler.class));
        final HttpMockResponse response = mock(HttpMockResponse.class);

        // Given
        given(handler.get(path)).willReturn(response);
        given(response.getStatus()).willReturn(OK);

        // When
        final Response actual = createClient(http).path(path).request().get();

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
        final Response actual = createClient(http).request().post(text(null), Response.class);

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
        final Response actual = createClient(http).request().put(text(""));

        // Then
        assertThat(actual.getStatus(), is(OK));
    }

    @Test
    public void Can_mock_an_http_patch_request() {

        final HttpMockHandler handler = http.mock(mock(HttpMockHandler.class));
        final HttpMockResponse response = mock(HttpMockResponse.class);

        // Given
        given(handler.patch()).willReturn(response);
        given(response.getStatus()).willReturn(OK);

        // When
        final Response actual = createClient(http).request().method("PATCH");

        // Then
        assertThat(actual.getStatus(), is(OK));
    }

    @Test
    public void Can_mock_an_http_delete_request() {

        final HttpMockHandler handler = http.mock(mock(HttpMockHandler.class));
        final HttpMockResponse response = mock(HttpMockResponse.class);

        // Given
        given(handler.delete()).willReturn(response);
        given(response.getStatus()).willReturn(OK);

        // When
        final Response actual = createClient(http).request().delete();

        // Then
        assertThat(actual.getStatus(), is(OK));
    }

    @Test
    public void Can_mock_an_http_options_request() {

        final HttpMockHandler handler = http.mock(mock(HttpMockHandler.class));
        final HttpMockResponse response = mock(HttpMockResponse.class);

        // Given
        given(handler.options()).willReturn(response);
        given(response.getStatus()).willReturn(OK);

        // When
        final Response actual = createClient(http).request().options();

        // Then
        assertThat(actual.getStatus(), is(OK));
    }

    @Test
    public void Can_mock_an_http_head_request() {

        final HttpMockHandler handler = http.mock(mock(HttpMockHandler.class));
        final HttpMockResponse response = mock(HttpMockResponse.class);

        // Given
        given(handler.head()).willReturn(response);
        given(response.getStatus()).willReturn(OK);

        // When
        final Response actual = createClient(http).request().head();

        // Then
        assertThat(actual.getStatus(), is(OK));
    }

    @Test
    public void Can_mock_an_http_trace_request() {

        final HttpMockHandler handler = http.mock(mock(HttpMockHandler.class));
        final HttpMockResponse response = mock(HttpMockResponse.class);

        // Given
        given(handler.trace()).willReturn(response);
        given(response.getStatus()).willReturn(OK);

        // When
        final Response actual = createClient(http).request().trace();

        // Then
        assertThat(actual.getStatus(), is(OK));
    }

    @Test
    public void Can_mock_a_non_standard_http_request() {

        final String method = someAlphaString(5);

        final HttpMockHandler handler = http.mock(mock(HttpMockHandler.class));
        final HttpMockResponse response = mock(HttpMockResponse.class);

        // Given
        given(handler.request(method)).willReturn(response);
        given(response.getStatus()).willReturn(OK);

        // When
        final Response actual = createClient(http).request().method(method);

        // Then
        assertThat(actual.getStatus(), is(OK));
    }

    private static String somePath() {
        return "/" + buildSomeString().thatContainsAlphanumericCharacters().withLengthBetween(1, 10).build();
    }
}

