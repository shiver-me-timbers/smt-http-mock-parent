package shiver.me.timbers.http.mock.integration;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import shiver.me.timbers.http.Headers;
import shiver.me.timbers.http.mock.HttpMockHandler;
import shiver.me.timbers.http.mock.HttpMockResponse;
import shiver.me.timbers.http.mock.HttpMockServer;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import static javax.ws.rs.client.Entity.text;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static shiver.me.timbers.data.random.RandomStrings.someAlphaString;
import static shiver.me.timbers.http.Methods.PATCH;
import static shiver.me.timbers.http.StatusCodes.NOT_FOUND;
import static shiver.me.timbers.http.StatusCodes.OK;
import static shiver.me.timbers.http.mock.HttpMock.h;
import static shiver.me.timbers.http.mock.HttpMock.headers;
import static shiver.me.timbers.http.mock.integration.HttpClients.createClient;
import static shiver.me.timbers.http.mock.integration.RandomHttp.somePath;

public class ITHttpRequestWithHeaders {

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
    public void Can_mock_an_http_get_request_with_headers() {

        final String path = somePath();
        final String otherPath = somePath();
        final HttpMockHandler handler = http.mock(mock(HttpMockHandler.class));
        final HttpMockResponse response = mock(HttpMockResponse.class);
        final String name1 = someAlphaString(4);
        final String name2 = someAlphaString(4);
        final String name3 = someAlphaString(4);
        final String value1 = someAlphaString(6);
        final String value2 = someAlphaString(6);
        final String value3 = someAlphaString(6);
        final MultivaluedMap<String, Object> headerMap = new MultivaluedHashMap<String, Object>() {{
            putSingle(name1, value1);
            putSingle(name2, value2);
            putSingle(name3, value3);
        }};

        // Given
        given(handler.get(path, headers(h(name1, value1), h(name2, value2), h(name3, value3)))).willReturn(response);
        given(response.getStatus()).willReturn(OK);

        // When
        final Response ok = createClient(http).path(path).request().headers(headerMap).get();
        final Response notFound = createClient(http).path(otherPath).request().headers(headerMap).get();

        // Then
        final Headers headers = headers(h(name1, value1), h(name2, value2), h(name3, value3));
        then(handler).should().get(path, headers);
        then(handler).should().get(otherPath, headers);
        assertThat(ok.getStatus(), is(OK));
        assertThat(notFound.getStatus(), is(NOT_FOUND));
    }

    @Test
    public void Can_mock_an_http_post_request_with_headers() {

        final String path = somePath();
        final String otherPath = somePath();
        final HttpMockHandler handler = http.mock(mock(HttpMockHandler.class));
        final HttpMockResponse response = mock(HttpMockResponse.class);
        final String name1 = someAlphaString(4);
        final String name2 = someAlphaString(4);
        final String name3 = someAlphaString(4);
        final String value1 = someAlphaString(6);
        final String value2 = someAlphaString(6);
        final String value3 = someAlphaString(6);
        final MultivaluedMap<String, Object> headerMap = new MultivaluedHashMap<String, Object>() {{
            putSingle(name1, value1);
            putSingle(name2, value2);
            putSingle(name3, value3);
        }};

        // Given
        given(handler.post(path, headers(h(name1, value1), h(name2, value2), h(name3, value3)))).willReturn(response);
        given(response.getStatus()).willReturn(OK);

        // When
        final Response ok = createClient(http).path(path).request().headers(headerMap).post(text(null), Response.class);
        final Response notFound = createClient(http).path(otherPath).request().headers(headerMap).post(text(null), Response.class);

        // Then
        final Headers headers = headers(h(name1, value1), h(name2, value2), h(name3, value3));
        then(handler).should().post(path, headers);
        then(handler).should().post(otherPath, headers);
        assertThat(ok.getStatus(), is(OK));
        assertThat(notFound.getStatus(), is(NOT_FOUND));
    }

    @Test
    public void Can_mock_an_http_put_request_with_headers() {

        final String path = somePath();
        final String otherPath = somePath();
        final HttpMockHandler handler = http.mock(mock(HttpMockHandler.class));
        final HttpMockResponse response = mock(HttpMockResponse.class);
        final String name1 = someAlphaString(4);
        final String name2 = someAlphaString(4);
        final String name3 = someAlphaString(4);
        final String value1 = someAlphaString(6);
        final String value2 = someAlphaString(6);
        final String value3 = someAlphaString(6);
        final MultivaluedMap<String, Object> headerMap = new MultivaluedHashMap<String, Object>() {{
            putSingle(name1, value1);
            putSingle(name2, value2);
            putSingle(name3, value3);
        }};

        // Given
        given(handler.put(path, headers(h(name1, value1), h(name2, value2), h(name3, value3)))).willReturn(response);
        given(response.getStatus()).willReturn(OK);

        // When
        final Response ok = createClient(http).path(path).request().headers(headerMap).put(text(""));
        final Response notFound = createClient(http).path(otherPath).request().headers(headerMap).put(text(""));

        // Then
        final Headers headers = headers(h(name1, value1), h(name2, value2), h(name3, value3));
        then(handler).should().put(path, headers);
        then(handler).should().put(otherPath, headers);
        assertThat(ok.getStatus(), is(OK));
        assertThat(notFound.getStatus(), is(NOT_FOUND));
    }

    @Test
    public void Can_mock_an_http_patch_request_with_headers() {

        final String path = somePath();
        final String otherPath = somePath();
        final HttpMockHandler handler = http.mock(mock(HttpMockHandler.class));
        final HttpMockResponse response = mock(HttpMockResponse.class);
        final String name1 = someAlphaString(4);
        final String name2 = someAlphaString(4);
        final String name3 = someAlphaString(4);
        final String value1 = someAlphaString(6);
        final String value2 = someAlphaString(6);
        final String value3 = someAlphaString(6);
        final MultivaluedMap<String, Object> headerMap = new MultivaluedHashMap<String, Object>() {{
            putSingle(name1, value1);
            putSingle(name2, value2);
            putSingle(name3, value3);
        }};

        // Given
        given(handler.patch(path, headers(h(name1, value1), h(name2, value2), h(name3, value3)))).willReturn(response);
        given(response.getStatus()).willReturn(OK);

        // When
        final Response ok = createClient(http).path(path).request().headers(headerMap).method(PATCH);
        final Response notFound = createClient(http).path(otherPath).request().headers(headerMap).method(PATCH);

        // Then
        final Headers headers = headers(h(name1, value1), h(name2, value2), h(name3, value3));
        then(handler).should().patch(path, headers);
        then(handler).should().patch(otherPath, headers);
        assertThat(ok.getStatus(), is(OK));
        assertThat(notFound.getStatus(), is(NOT_FOUND));
    }

    @Test
    public void Can_mock_an_http_delete_request_with_headers() {

        final String path = somePath();
        final String otherPath = somePath();
        final HttpMockHandler handler = http.mock(mock(HttpMockHandler.class));
        final HttpMockResponse response = mock(HttpMockResponse.class);
        final String name1 = someAlphaString(4);
        final String name2 = someAlphaString(4);
        final String name3 = someAlphaString(4);
        final String value1 = someAlphaString(6);
        final String value2 = someAlphaString(6);
        final String value3 = someAlphaString(6);
        final MultivaluedMap<String, Object> headerMap = new MultivaluedHashMap<String, Object>() {{
            putSingle(name1, value1);
            putSingle(name2, value2);
            putSingle(name3, value3);
        }};

        // Given
        given(handler.delete(path, headers(h(name1, value1), h(name2, value2), h(name3, value3)))).willReturn(response);
        given(response.getStatus()).willReturn(OK);

        // When
        final Response ok = createClient(http).path(path).request().headers(headerMap).delete();
        final Response notFound = createClient(http).path(otherPath).request().headers(headerMap).delete();

        // Then
        final Headers headers = headers(h(name1, value1), h(name2, value2), h(name3, value3));
        then(handler).should().delete(path, headers);
        then(handler).should().delete(otherPath, headers);
        assertThat(ok.getStatus(), is(OK));
        assertThat(notFound.getStatus(), is(NOT_FOUND));
    }

    @Test
    public void Can_mock_an_http_options_request_with_headers() {

        final String path = somePath();
        final String otherPath = somePath();
        final HttpMockHandler handler = http.mock(mock(HttpMockHandler.class));
        final HttpMockResponse response = mock(HttpMockResponse.class);
        final String name1 = someAlphaString(4);
        final String name2 = someAlphaString(4);
        final String name3 = someAlphaString(4);
        final String value1 = someAlphaString(6);
        final String value2 = someAlphaString(6);
        final String value3 = someAlphaString(6);
        final MultivaluedMap<String, Object> headerMap = new MultivaluedHashMap<String, Object>() {{
            putSingle(name1, value1);
            putSingle(name2, value2);
            putSingle(name3, value3);
        }};

        // Given
        given(handler.options(path, headers(h(name1, value1), h(name2, value2), h(name3, value3)))).willReturn(response);
        given(response.getStatus()).willReturn(OK);

        // When
        final Response ok = createClient(http).path(path).request().headers(headerMap).options();
        final Response notFound = createClient(http).path(otherPath).request().headers(headerMap).options();

        // Then
        final Headers headers = headers(h(name1, value1), h(name2, value2), h(name3, value3));
        then(handler).should().options(path, headers);
        then(handler).should().options(otherPath, headers);
        assertThat(ok.getStatus(), is(OK));
        assertThat(notFound.getStatus(), is(NOT_FOUND));
    }

    @Test
    public void Can_mock_an_http_head_request_with_headers() {

        final String path = somePath();
        final String otherPath = somePath();
        final HttpMockHandler handler = http.mock(mock(HttpMockHandler.class));
        final HttpMockResponse response = mock(HttpMockResponse.class);
        final String name1 = someAlphaString(4);
        final String name2 = someAlphaString(4);
        final String name3 = someAlphaString(4);
        final String value1 = someAlphaString(6);
        final String value2 = someAlphaString(6);
        final String value3 = someAlphaString(6);
        final MultivaluedMap<String, Object> headerMap = new MultivaluedHashMap<String, Object>() {{
            putSingle(name1, value1);
            putSingle(name2, value2);
            putSingle(name3, value3);
        }};

        // Given
        given(handler.head(path, headers(h(name1, value1), h(name2, value2), h(name3, value3)))).willReturn(response);
        given(response.getStatus()).willReturn(OK);

        // When
        final Response ok = createClient(http).path(path).request().headers(headerMap).head();
        final Response notFound = createClient(http).path(otherPath).request().headers(headerMap).head();

        // Then
        final Headers headers = headers(h(name1, value1), h(name2, value2), h(name3, value3));
        then(handler).should().head(path, headers);
        then(handler).should().head(otherPath, headers);
        assertThat(ok.getStatus(), is(OK));
        assertThat(notFound.getStatus(), is(NOT_FOUND));
    }

    @Test
    public void Can_mock_an_http_trace_request_with_headers() {

        final String path = somePath();
        final String otherPath = somePath();
        final HttpMockHandler handler = http.mock(mock(HttpMockHandler.class));
        final HttpMockResponse response = mock(HttpMockResponse.class);
        final String name1 = someAlphaString(4);
        final String name2 = someAlphaString(4);
        final String name3 = someAlphaString(4);
        final String value1 = someAlphaString(6);
        final String value2 = someAlphaString(6);
        final String value3 = someAlphaString(6);
        final MultivaluedMap<String, Object> headerMap = new MultivaluedHashMap<String, Object>() {{
            putSingle(name1, value1);
            putSingle(name2, value2);
            putSingle(name3, value3);
        }};

        // Given
        given(handler.trace(path, headers(h(name1, value1), h(name2, value2), h(name3, value3)))).willReturn(response);
        given(response.getStatus()).willReturn(OK);

        // When
        final Response ok = createClient(http).path(path).request().headers(headerMap).trace();
        final Response notFound = createClient(http).path(otherPath).request().headers(headerMap).trace();

        // Then
        final Headers headers = headers(h(name1, value1), h(name2, value2), h(name3, value3));
        then(handler).should().trace(path, headers);
        then(handler).should().trace(otherPath, headers);
        assertThat(ok.getStatus(), is(OK));
        assertThat(notFound.getStatus(), is(NOT_FOUND));
    }

    @Test
    public void Can_mock_an_http_non_standard_request_with_headers() {

        final String path = somePath();
        final String otherPath = somePath();
        final String method = "CUSTOM";
        final CustomHttpMethodHandler handler = http.mock(mock(CustomHttpMethodHandler.class));
        final HttpMockResponse response = mock(HttpMockResponse.class);
        final String name1 = someAlphaString(4);
        final String name2 = someAlphaString(4);
        final String name3 = someAlphaString(4);
        final String value1 = someAlphaString(6);
        final String value2 = someAlphaString(6);
        final String value3 = someAlphaString(6);
        final MultivaluedMap<String, Object> headerMap = new MultivaluedHashMap<String, Object>() {{
            putSingle(name1, value1);
            putSingle(name2, value2);
            putSingle(name3, value3);
        }};

        // Given
        given(handler.custom(path, headers(h(name1, value1), h(name2, value2), h(name3, value3))))
            .willReturn(response);
        given(response.getStatus()).willReturn(OK);

        // When
        final Response ok = createClient(http).path(path).request().headers(headerMap).method(method);
        final Response notFound = createClient(http).path(otherPath).request().headers(headerMap).method(method);

        // Then
        final Headers headers = headers(h(name1, value1), h(name2, value2), h(name3, value3));
        then(handler).should().custom(path, headers);
        then(handler).should().custom(otherPath, headers);
        assertThat(ok.getStatus(), is(OK));
        assertThat(notFound.getStatus(), is(NOT_FOUND));
    }
}

