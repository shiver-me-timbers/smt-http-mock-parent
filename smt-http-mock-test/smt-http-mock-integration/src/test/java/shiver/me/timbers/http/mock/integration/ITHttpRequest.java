package shiver.me.timbers.http.mock.integration;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import shiver.me.timbers.http.mock.HttpMockHandler;
import shiver.me.timbers.http.mock.HttpMockResponse;
import shiver.me.timbers.http.mock.HttpMockServer;

import javax.ws.rs.core.Response;

import static java.lang.String.format;
import static javax.ws.rs.client.Entity.text;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyString;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static shiver.me.timbers.data.random.RandomStrings.someAlphaString;
import static shiver.me.timbers.http.Methods.DELETE;
import static shiver.me.timbers.http.Methods.GET;
import static shiver.me.timbers.http.Methods.OPTIONS;
import static shiver.me.timbers.http.Methods.PATCH;
import static shiver.me.timbers.http.Methods.POST;
import static shiver.me.timbers.http.Methods.PUT;
import static shiver.me.timbers.http.Methods.TRACE;
import static shiver.me.timbers.http.StatusCodes.NOT_FOUND;
import static shiver.me.timbers.http.StatusCodes.OK;
import static shiver.me.timbers.http.mock.integration.HttpClients.createClient;
import static shiver.me.timbers.http.mock.integration.RandomHttp.somePath;

public class ITHttpRequest {

    private static HttpMockServer http;

    @BeforeClass
    public static void setUp() {
        http = new HttpMockServer();
        http.ignoreHeaders("Host", "Connection", "User-Agent", "Accept", "Content-Type", "Content-Length");
    }

    @AfterClass
    public static void tearDown() {
        http.stop();
    }

    @Test
    public void Can_mock_an_http_get_request() {

        final String path = somePath();
        final String otherPath = somePath();
        final HttpMockHandler handler = http.mock(mock(HttpMockHandler.class));
        final HttpMockResponse response = mock(HttpMockResponse.class);

        // Given
        given(handler.get(path)).willReturn(response);
        given(response.getStatus()).willReturn(OK);

        // When
        final Response ok = createClient(http).path(path).request().get();
        final Response notFound = createClient(http).path(otherPath).request().get();

        // Then
        then(handler).should().get(path);
        then(handler).should().get(otherPath);
        verifyNoMoreInteractions(handler);
        assertThat(ok.getStatus(), is(OK));
        assertThat(notFound.getStatus(), is(NOT_FOUND));
        assertThat(notFound.readEntity(String.class), is(notFoundMessage(GET, otherPath)));
    }

    @Test
    public void Can_mock_an_http_post_request() {

        final String path = somePath();
        final String otherPath = somePath();
        final HttpMockHandler handler = http.mock(mock(HttpMockHandler.class));
        final HttpMockResponse response = mock(HttpMockResponse.class);

        // Given
        given(handler.post(path)).willReturn(response);
        given(response.getStatus()).willReturn(OK);

        // When
        final Response actual = createClient(http).path(path).request().post(text(null), Response.class);
        final Response notFound = createClient(http).path(otherPath).request().post(text(null), Response.class);

        // Then
        then(handler).should().post(path);
        then(handler).should().post(otherPath);
        verifyNoMoreInteractions(handler);
        assertThat(actual.getStatus(), is(OK));
        assertThat(notFound.getStatus(), is(NOT_FOUND));
        assertThat(notFound.readEntity(String.class), is(notFoundMessage(POST, otherPath)));
    }

    @Test
    public void Can_mock_an_http_put_request() {

        final String path = somePath();
        final String otherPath = somePath();
        final HttpMockHandler handler = http.mock(mock(HttpMockHandler.class));
        final HttpMockResponse response = mock(HttpMockResponse.class);

        // Given
        given(handler.put(path)).willReturn(response);
        given(response.getStatus()).willReturn(OK);

        // When
        final Response actual = createClient(http).path(path).request().put(text(""));
        final Response notFound = createClient(http).path(otherPath).request().put(text(""));

        // Then
        then(handler).should().put(path);
        then(handler).should().put(otherPath);
        verifyNoMoreInteractions(handler);
        assertThat(actual.getStatus(), is(OK));
        assertThat(notFound.getStatus(), is(NOT_FOUND));
        assertThat(notFound.readEntity(String.class), is(notFoundMessage(PUT, otherPath)));
    }

    @Test
    public void Can_mock_an_http_patch_request() {

        final String path = somePath();
        final String otherPath = somePath();
        final HttpMockHandler handler = http.mock(mock(HttpMockHandler.class));
        final HttpMockResponse response = mock(HttpMockResponse.class);

        // Given
        given(handler.patch(path)).willReturn(response);
        given(response.getStatus()).willReturn(OK);

        // When
        final Response actual = createClient(http).path(path).request().method(PATCH);
        final Response notFound = createClient(http).path(otherPath).request().method(PATCH);

        // Then
        then(handler).should().patch(path);
        then(handler).should().patch(otherPath);
        verifyNoMoreInteractions(handler);
        assertThat(actual.getStatus(), is(OK));
        assertThat(notFound.getStatus(), is(NOT_FOUND));
        assertThat(notFound.readEntity(String.class), is(notFoundMessage(PATCH, otherPath)));
    }

    @Test
    public void Can_mock_an_http_delete_request() {

        final String path = somePath();
        final String otherPath = somePath();
        final HttpMockHandler handler = http.mock(mock(HttpMockHandler.class));
        final HttpMockResponse response = mock(HttpMockResponse.class);

        // Given
        given(handler.delete(path)).willReturn(response);
        given(response.getStatus()).willReturn(OK);

        // When
        final Response actual = createClient(http).path(path).request().delete();
        final Response notFound = createClient(http).path(otherPath).request().delete();

        // Then
        then(handler).should().delete(path);
        then(handler).should().delete(otherPath);
        verifyNoMoreInteractions(handler);
        assertThat(actual.getStatus(), is(OK));
        assertThat(notFound.getStatus(), is(NOT_FOUND));
        assertThat(notFound.readEntity(String.class), is(notFoundMessage(DELETE, otherPath)));
    }

    @Test
    public void Can_mock_an_http_options_request() {

        final String path = somePath();
        final String otherPath = somePath();
        final HttpMockHandler handler = http.mock(mock(HttpMockHandler.class));
        final HttpMockResponse response = mock(HttpMockResponse.class);

        // Given
        given(handler.options(path)).willReturn(response);
        given(response.getStatus()).willReturn(OK);

        // When
        final Response actual = createClient(http).path(path).request().options();
        final Response notFound = createClient(http).path(otherPath).request().options();

        // Then
        then(handler).should().options(path);
        then(handler).should().options(otherPath);
        verifyNoMoreInteractions(handler);
        assertThat(actual.getStatus(), is(OK));
        assertThat(notFound.getStatus(), is(NOT_FOUND));
        assertThat(notFound.readEntity(String.class), is(notFoundMessage(OPTIONS, otherPath)));
    }

    @Test
    public void Can_mock_an_http_head_request() {

        final String path = somePath();
        final String otherPath = somePath();
        final HttpMockHandler handler = http.mock(mock(HttpMockHandler.class));
        final HttpMockResponse response = mock(HttpMockResponse.class);

        // Given
        given(handler.head(path)).willReturn(response);
        given(response.getStatus()).willReturn(OK);

        // When
        final Response actual = createClient(http).path(path).request().head();
        final Response notFound = createClient(http).path(otherPath).request().head();

        // Then
        then(handler).should().head(path);
        then(handler).should().head(otherPath);
        verifyNoMoreInteractions(handler);
        assertThat(actual.getStatus(), is(OK));
        assertThat(notFound.getStatus(), is(NOT_FOUND));
        assertThat(notFound.readEntity(String.class), isEmptyString());
    }

    @Test
    public void Can_mock_an_http_trace_request() {

        final String path = somePath();
        final String otherPath = somePath();
        final HttpMockHandler handler = http.mock(mock(HttpMockHandler.class));
        final HttpMockResponse response = mock(HttpMockResponse.class);

        // Given
        given(handler.trace(path)).willReturn(response);
        given(response.getStatus()).willReturn(OK);

        // When
        final Response actual = createClient(http).path(path).request().trace();
        final Response notFound = createClient(http).path(otherPath).request().trace();

        // Then
        then(handler).should().trace(path);
        then(handler).should().trace(otherPath);
        verifyNoMoreInteractions(handler);
        assertThat(actual.getStatus(), is(OK));
        assertThat(notFound.getStatus(), is(NOT_FOUND));
        assertThat(notFound.readEntity(String.class), is(notFoundMessage(TRACE, otherPath)));
    }

    @Test
    public void Can_mock_a_non_standard_http_request() {

        final String path = somePath();
        final String otherPath = somePath();
        final String method = someAlphaString(5);

        final HttpMockHandler handler = http.mock(mock(HttpMockHandler.class));
        final HttpMockResponse response = mock(HttpMockResponse.class);

        // Given
        given(handler.request(method, path)).willReturn(response);
        given(response.getStatus()).willReturn(OK);

        // When
        final Response actual = createClient(http).path(path).request().method(method);
        final Response notFound = createClient(http).path(otherPath).request().method(method);

        // Then
        then(handler).should().request(method, path);
        then(handler).should().request(method, otherPath);
        verifyNoMoreInteractions(handler);
        assertThat(actual.getStatus(), is(OK));
        assertThat(notFound.getStatus(), is(NOT_FOUND));
        assertThat(notFound.readEntity(String.class), is(notFoundMessage(method, otherPath)));
    }

    private static String notFoundMessage(String method, String path) {
        return format("The %s request with path (%s) has not been mocked.", method, path);
    }
}

