package shiver.me.timbers.http.mock.integration;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import shiver.me.timbers.http.mock.HttpMockHandler;
import shiver.me.timbers.http.mock.HttpMockResponse;
import shiver.me.timbers.http.mock.HttpMockServer;

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
import static shiver.me.timbers.http.mock.integration.CustomHttpMethodHandler.CUSTOM;
import static shiver.me.timbers.http.mock.integration.HttpClients.createClient;
import static shiver.me.timbers.http.mock.integration.RandomHttp.someHeaders;
import static shiver.me.timbers.http.mock.integration.RandomHttp.somePath;
import static shiver.me.timbers.http.mock.integration.RandomHttp.toHeaders;
import static shiver.me.timbers.http.mock.integration.RandomHttp.toMap;

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
        final HttpMockHandler handler = http.mock(mock(HttpMockHandler.class));
        final HttpMockResponse response = mock(HttpMockResponse.class);
        final String name1 = someAlphaString(4);
        final String name2 = someAlphaString(4);
        final String name3 = someAlphaString(4);
        final String value1 = someAlphaString(6);
        final String value2 = someAlphaString(6);
        final String value3 = someAlphaString(6);
        final MultivaluedMap<String, Object> headerMap = toMap(name1, value1, name2, value2, name3, value3);
        final MultivaluedMap<String, Object> otherHeaderMap = someHeaders();

        // Given
        given(handler.get(path, headers(h(name1, value1), h(name2, value2), h(name3, value3)))).willReturn(response);
        given(response.getStatus()).willReturn(OK);

        // When
        final Response ok = createClient(http).path(path).request().headers(headerMap).get();
        final Response notFound = createClient(http).path(path).request().headers(otherHeaderMap).get();

        // Then
        then(handler).should().get(path, toHeaders(headerMap));
        then(handler).should().get(path, toHeaders(otherHeaderMap));
        assertThat(ok.getStatus(), is(OK));
        assertThat(notFound.getStatus(), is(NOT_FOUND));
    }

    @Test
    public void Can_mock_an_http_post_request_with_headers() {

        final String path = somePath();
        final HttpMockHandler handler = http.mock(mock(HttpMockHandler.class));
        final HttpMockResponse response = mock(HttpMockResponse.class);
        final String name1 = someAlphaString(4);
        final String name2 = someAlphaString(4);
        final String name3 = someAlphaString(4);
        final String value1 = someAlphaString(6);
        final String value2 = someAlphaString(6);
        final String value3 = someAlphaString(6);
        final MultivaluedMap<String, Object> headerMap = toMap(name1, value1, name2, value2, name3, value3);
        final MultivaluedMap<String, Object> otherHeaderMap = someHeaders();

        // Given
        given(handler.post(path, headers(h(name1, value1), h(name2, value2), h(name3, value3)))).willReturn(response);
        given(response.getStatus()).willReturn(OK);

        // When
        final Response ok = createClient(http).path(path).request().headers(headerMap).post(text(null), Response.class);
        final Response notFound = createClient(http).path(path).request().headers(otherHeaderMap).post(text(null), Response.class);

        // Then
        then(handler).should().post(path, toHeaders(headerMap));
        then(handler).should().post(path, toHeaders(otherHeaderMap));
        assertThat(ok.getStatus(), is(OK));
        assertThat(notFound.getStatus(), is(NOT_FOUND));
    }

    @Test
    public void Can_mock_an_http_put_request_with_headers() {

        final String path = somePath();
        final HttpMockHandler handler = http.mock(mock(HttpMockHandler.class));
        final HttpMockResponse response = mock(HttpMockResponse.class);
        final String name1 = someAlphaString(4);
        final String name2 = someAlphaString(4);
        final String name3 = someAlphaString(4);
        final String value1 = someAlphaString(6);
        final String value2 = someAlphaString(6);
        final String value3 = someAlphaString(6);
        final MultivaluedMap<String, Object> headerMap = toMap(name1, value1, name2, value2, name3, value3);
        final MultivaluedMap<String, Object> otherHeaderMap = someHeaders();

        // Given
        given(handler.put(path, headers(h(name1, value1), h(name2, value2), h(name3, value3)))).willReturn(response);
        given(response.getStatus()).willReturn(OK);

        // When
        final Response ok = createClient(http).path(path).request().headers(headerMap).put(text(""));
        final Response notFound = createClient(http).path(path).request().headers(otherHeaderMap).put(text(""));

        // Then
        then(handler).should().put(path, toHeaders(headerMap));
        then(handler).should().put(path, toHeaders(otherHeaderMap));
        assertThat(ok.getStatus(), is(OK));
        assertThat(notFound.getStatus(), is(NOT_FOUND));
    }

    @Test
    public void Can_mock_an_http_patch_request_with_headers() {

        final String path = somePath();
        final HttpMockHandler handler = http.mock(mock(HttpMockHandler.class));
        final HttpMockResponse response = mock(HttpMockResponse.class);
        final String name1 = someAlphaString(4);
        final String name2 = someAlphaString(4);
        final String name3 = someAlphaString(4);
        final String value1 = someAlphaString(6);
        final String value2 = someAlphaString(6);
        final String value3 = someAlphaString(6);
        final MultivaluedMap<String, Object> headerMap = toMap(name1, value1, name2, value2, name3, value3);
        final MultivaluedMap<String, Object> otherHeaderMap = someHeaders();

        // Given
        given(handler.patch(path, headers(h(name1, value1), h(name2, value2), h(name3, value3)))).willReturn(response);
        given(response.getStatus()).willReturn(OK);

        // When
        final Response ok = createClient(http).path(path).request().headers(headerMap).method(PATCH);
        final Response notFound = createClient(http).path(path).request().headers(otherHeaderMap).method(PATCH);

        // Then
        then(handler).should().patch(path, toHeaders(headerMap));
        then(handler).should().patch(path, toHeaders(otherHeaderMap));
        assertThat(ok.getStatus(), is(OK));
        assertThat(notFound.getStatus(), is(NOT_FOUND));
    }

    @Test
    public void Can_mock_an_http_delete_request_with_headers() {

        final String path = somePath();
        final HttpMockHandler handler = http.mock(mock(HttpMockHandler.class));
        final HttpMockResponse response = mock(HttpMockResponse.class);
        final String name1 = someAlphaString(4);
        final String name2 = someAlphaString(4);
        final String name3 = someAlphaString(4);
        final String value1 = someAlphaString(6);
        final String value2 = someAlphaString(6);
        final String value3 = someAlphaString(6);
        final MultivaluedMap<String, Object> headerMap = toMap(name1, value1, name2, value2, name3, value3);
        final MultivaluedMap<String, Object> otherHeaderMap = someHeaders();

        // Given
        given(handler.delete(path, headers(h(name1, value1), h(name2, value2), h(name3, value3)))).willReturn(response);
        given(response.getStatus()).willReturn(OK);

        // When
        final Response ok = createClient(http).path(path).request().headers(headerMap).delete();
        final Response notFound = createClient(http).path(path).request().headers(otherHeaderMap).delete();

        // Then
        then(handler).should().delete(path, toHeaders(headerMap));
        then(handler).should().delete(path, toHeaders(otherHeaderMap));
        assertThat(ok.getStatus(), is(OK));
        assertThat(notFound.getStatus(), is(NOT_FOUND));
    }

    @Test
    public void Can_mock_an_http_options_request_with_headers() {

        final String path = somePath();
        final HttpMockHandler handler = http.mock(mock(HttpMockHandler.class));
        final HttpMockResponse response = mock(HttpMockResponse.class);
        final String name1 = someAlphaString(4);
        final String name2 = someAlphaString(4);
        final String name3 = someAlphaString(4);
        final String value1 = someAlphaString(6);
        final String value2 = someAlphaString(6);
        final String value3 = someAlphaString(6);
        final MultivaluedMap<String, Object> headerMap = toMap(name1, value1, name2, value2, name3, value3);
        final MultivaluedMap<String, Object> otherHeaderMap = someHeaders();

        // Given
        given(handler.options(path, headers(h(name1, value1), h(name2, value2), h(name3, value3)))).willReturn(response);
        given(response.getStatus()).willReturn(OK);

        // When
        final Response ok = createClient(http).path(path).request().headers(headerMap).options();
        final Response notFound = createClient(http).path(path).request().headers(otherHeaderMap).options();

        // Then
        then(handler).should().options(path, toHeaders(headerMap));
        then(handler).should().options(path, toHeaders(otherHeaderMap));
        assertThat(ok.getStatus(), is(OK));
        assertThat(notFound.getStatus(), is(NOT_FOUND));
    }

    @Test
    public void Can_mock_an_http_head_request_with_headers() {

        final String path = somePath();
        final HttpMockHandler handler = http.mock(mock(HttpMockHandler.class));
        final HttpMockResponse response = mock(HttpMockResponse.class);
        final String name1 = someAlphaString(4);
        final String name2 = someAlphaString(4);
        final String name3 = someAlphaString(4);
        final String value1 = someAlphaString(6);
        final String value2 = someAlphaString(6);
        final String value3 = someAlphaString(6);
        final MultivaluedMap<String, Object> headerMap = toMap(name1, value1, name2, value2, name3, value3);
        final MultivaluedMap<String, Object> otherHeaderMap = someHeaders();

        // Given
        given(handler.head(path, headers(h(name1, value1), h(name2, value2), h(name3, value3)))).willReturn(response);
        given(response.getStatus()).willReturn(OK);

        // When
        final Response ok = createClient(http).path(path).request().headers(headerMap).head();
        final Response notFound = createClient(http).path(path).request().headers(otherHeaderMap).head();

        // Then
        then(handler).should().head(path, toHeaders(headerMap));
        then(handler).should().head(path, toHeaders(otherHeaderMap));
        assertThat(ok.getStatus(), is(OK));
        assertThat(notFound.getStatus(), is(NOT_FOUND));
    }

    @Test
    public void Can_mock_an_http_trace_request_with_headers() {

        final String path = somePath();
        final HttpMockHandler handler = http.mock(mock(HttpMockHandler.class));
        final HttpMockResponse response = mock(HttpMockResponse.class);
        final String name1 = someAlphaString(4);
        final String name2 = someAlphaString(4);
        final String name3 = someAlphaString(4);
        final String value1 = someAlphaString(6);
        final String value2 = someAlphaString(6);
        final String value3 = someAlphaString(6);
        final MultivaluedMap<String, Object> headerMap = toMap(name1, value1, name2, value2, name3, value3);
        final MultivaluedMap<String, Object> otherHeaderMap = someHeaders();

        // Given
        given(handler.trace(path, headers(h(name1, value1), h(name2, value2), h(name3, value3)))).willReturn(response);
        given(response.getStatus()).willReturn(OK);

        // When
        final Response ok = createClient(http).path(path).request().headers(headerMap).trace();
        final Response notFound = createClient(http).path(path).request().headers(otherHeaderMap).trace();

        // Then
        then(handler).should().trace(path, toHeaders(headerMap));
        then(handler).should().trace(path, toHeaders(otherHeaderMap));
        assertThat(ok.getStatus(), is(OK));
        assertThat(notFound.getStatus(), is(NOT_FOUND));
    }

    @Test
    public void Can_mock_an_http_non_standard_request_with_headers() {

        final String path = somePath();
        final CustomHttpMethodHandler handler = http.mock(mock(CustomHttpMethodHandler.class));
        final HttpMockResponse response = mock(HttpMockResponse.class);
        final String name1 = someAlphaString(4);
        final String name2 = someAlphaString(4);
        final String name3 = someAlphaString(4);
        final String value1 = someAlphaString(6);
        final String value2 = someAlphaString(6);
        final String value3 = someAlphaString(6);
        final MultivaluedMap<String, Object> headerMap = toMap(name1, value1, name2, value2, name3, value3);
        final MultivaluedMap<String, Object> otherHeaderMap = someHeaders();

        // Given
        given(handler.custom(path, headers(h(name1, value1), h(name2, value2), h(name3, value3))))
            .willReturn(response);
        given(response.getStatus()).willReturn(OK);

        // When
        final Response ok = createClient(http).path(path).request().headers(headerMap).method(CUSTOM);
        final Response notFound = createClient(http).path(path).request().headers(otherHeaderMap).method(CUSTOM);

        // Then
        then(handler).should().custom(path, toHeaders(headerMap));
        then(handler).should().custom(path, toHeaders(otherHeaderMap));
        assertThat(ok.getStatus(), is(OK));
        assertThat(notFound.getStatus(), is(NOT_FOUND));
    }
}

