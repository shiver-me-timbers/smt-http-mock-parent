package shiver.me.timbers.http.mock.integration;

import org.junit.Test;
import shiver.me.timbers.http.mock.HttpMockPATCH;
import shiver.me.timbers.http.mock.HttpMockPOST;
import shiver.me.timbers.http.mock.HttpMockPUT;
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
import static shiver.me.timbers.data.random.RandomStrings.someString;
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

public abstract class AbstractHttpRequestWithHeadersAndBody {

    private final HttpMockServer http;

    AbstractHttpRequestWithHeadersAndBody() {
        this.http = http();
    }

    protected abstract HttpMockServer http();

    @Test
    public void Can_mock_an_http_post_request_with_headers_and_a_body() {

        final String path = somePath();
        final String otherPath = somePath();
        final String body = someString();
        final String otherBody = someString();
        final String name1 = someAlphaString(4);
        final String name2 = someAlphaString(4);
        final String name3 = someAlphaString(4);
        final String value1 = someAlphaString(6);
        final String value2 = someAlphaString(6);
        final String value3 = someAlphaString(6);
        final MultivaluedMap<String, Object> headerMap = toMap(name1, value1, name2, value2, name3, value3);
        final MultivaluedMap<String, Object> otherHeaderMap = someHeaders();
        final HttpMockPOST handler = http.mock(mock(HttpMockPOST.class));
        final HttpMockResponse response = mock(HttpMockResponse.class);

        // Given
        given(handler.post(path, headers(h(name1, value1), h(name2, value2), h(name3, value3)), body))
            .willReturn(response);
        given(response.getStatus()).willReturn(OK);

        // When
        final Response ok = createClient(http).path(path).request().headers(headerMap).post(text(body));
        final Response invalidPath = createClient(http).path(otherPath).request().headers(headerMap).post(text(body));
        final Response invalidHeaders = createClient(http).path(path).request().headers(otherHeaderMap).post(text(body));
        final Response invalidBody = createClient(http).path(path).request().headers(headerMap).post(text(otherBody));

        // Then
        then(handler).should().post(path, toHeaders(headerMap), body);
        then(handler).should().post(otherPath, toHeaders(headerMap), body);
        then(handler).should().post(path, toHeaders(otherHeaderMap), body);
        then(handler).should().post(path, toHeaders(headerMap), otherBody);
        assertThat(ok.getStatus(), is(OK));
        assertThat(invalidPath.getStatus(), is(NOT_FOUND));
        assertThat(invalidHeaders.getStatus(), is(NOT_FOUND));
        assertThat(invalidBody.getStatus(), is(NOT_FOUND));
    }

    @Test
    public void Can_mock_an_http_put_request_with_headers_and_a_body() {

        final String path = somePath();
        final String otherPath = somePath();
        final String body = someString();
        final String otherBody = someString();
        final String name1 = someAlphaString(4);
        final String name2 = someAlphaString(4);
        final String name3 = someAlphaString(4);
        final String value1 = someAlphaString(6);
        final String value2 = someAlphaString(6);
        final String value3 = someAlphaString(6);
        final MultivaluedMap<String, Object> headerMap = toMap(name1, value1, name2, value2, name3, value3);
        final MultivaluedMap<String, Object> otherHeaderMap = someHeaders();
        final HttpMockPUT handler = http.mock(mock(HttpMockPUT.class));
        final HttpMockResponse response = mock(HttpMockResponse.class);

        // Given
        given(handler.put(path, headers(h(name1, value1), h(name2, value2), h(name3, value3)), body))
            .willReturn(response);
        given(response.getStatus()).willReturn(OK);

        // When
        final Response ok = createClient(http).path(path).request().headers(headerMap).put(text(body));
        final Response invalidPath = createClient(http).path(otherPath).request().headers(headerMap).put(text(body));
        final Response invalidHeaders = createClient(http).path(path).request().headers(otherHeaderMap).put(text(body));
        final Response invalidBody = createClient(http).path(path).request().headers(headerMap).put(text(otherBody));

        // Then
        then(handler).should().put(path, toHeaders(headerMap), body);
        then(handler).should().put(otherPath, toHeaders(headerMap), body);
        then(handler).should().put(path, toHeaders(otherHeaderMap), body);
        then(handler).should().put(path, toHeaders(headerMap), otherBody);
        assertThat(ok.getStatus(), is(OK));
        assertThat(invalidPath.getStatus(), is(NOT_FOUND));
        assertThat(invalidHeaders.getStatus(), is(NOT_FOUND));
        assertThat(invalidBody.getStatus(), is(NOT_FOUND));
    }

    @Test
    public void Can_mock_an_http_patch_request_with_headers_and_a_body() {

        final String path = somePath();
        final String otherPath = somePath();
        final String body = someString();
        final String otherBody = someString();
        final String name1 = someAlphaString(4);
        final String name2 = someAlphaString(4);
        final String name3 = someAlphaString(4);
        final String value1 = someAlphaString(6);
        final String value2 = someAlphaString(6);
        final String value3 = someAlphaString(6);
        final MultivaluedMap<String, Object> headerMap = toMap(name1, value1, name2, value2, name3, value3);
        final MultivaluedMap<String, Object> otherHeaderMap = someHeaders();
        final HttpMockPATCH handler = http.mock(mock(HttpMockPATCH.class));
        final HttpMockResponse response = mock(HttpMockResponse.class);

        // Given
        given(handler.patch(path, headers(h(name1, value1), h(name2, value2), h(name3, value3)), body))
            .willReturn(response);
        given(response.getStatus()).willReturn(OK);

        // When
        final Response ok = createClient(http).path(path).request().headers(headerMap).method(PATCH, text(body));
        final Response invalidPath = createClient(http).path(otherPath).request().headers(headerMap).method(PATCH, text(body));
        final Response invalidHeaders = createClient(http).path(path).request().headers(otherHeaderMap).method(PATCH, text(body));
        final Response invalidBody = createClient(http).path(path).request().headers(headerMap).method(PATCH, text(otherBody));

        // Then
        then(handler).should().patch(path, toHeaders(headerMap), body);
        then(handler).should().patch(otherPath, toHeaders(headerMap), body);
        then(handler).should().patch(path, toHeaders(otherHeaderMap), body);
        then(handler).should().patch(path, toHeaders(headerMap), otherBody);
        assertThat(ok.getStatus(), is(OK));
        assertThat(invalidPath.getStatus(), is(NOT_FOUND));
        assertThat(invalidHeaders.getStatus(), is(NOT_FOUND));
        assertThat(invalidBody.getStatus(), is(NOT_FOUND));
    }

    @Test
    public void Can_mock_a_non_standard_http_request_with_headers_and_a_body() {

        final String path = somePath();
        final String otherPath = somePath();
        final String body = someString();
        final String otherBody = someString();
        final String name1 = someAlphaString(4);
        final String name2 = someAlphaString(4);
        final String name3 = someAlphaString(4);
        final String value1 = someAlphaString(6);
        final String value2 = someAlphaString(6);
        final String value3 = someAlphaString(6);
        final MultivaluedMap<String, Object> headerMap = toMap(name1, value1, name2, value2, name3, value3);
        final MultivaluedMap<String, Object> otherHeaderMap = someHeaders();
        final CustomHttpMethodHandler handler = http.mock(mock(CustomHttpMethodHandler.class));
        final HttpMockResponse response = mock(HttpMockResponse.class);

        // Given
        given(handler.custom(path, headers(h(name1, value1), h(name2, value2), h(name3, value3)), body))
            .willReturn(response);
        given(response.getStatus()).willReturn(OK);

        // When
        final Response ok = createClient(http).path(path).request().headers(headerMap).method(CUSTOM, text(body));
        final Response invalidPath = createClient(http).path(otherPath).request().headers(headerMap).method(CUSTOM, text(body));
        final Response invalidHeaders = createClient(http).path(path).request().headers(otherHeaderMap).method(CUSTOM, text(body));
        final Response invalidBody = createClient(http).path(path).request().headers(headerMap).method(CUSTOM, text(otherBody));

        // Then
        then(handler).should().custom(path, toHeaders(headerMap), body);
        then(handler).should().custom(otherPath, toHeaders(headerMap), body);
        then(handler).should().custom(path, toHeaders(otherHeaderMap), body);
        then(handler).should().custom(path, toHeaders(headerMap), otherBody);
        assertThat(ok.getStatus(), is(OK));
        assertThat(invalidPath.getStatus(), is(NOT_FOUND));
        assertThat(invalidHeaders.getStatus(), is(NOT_FOUND));
        assertThat(invalidBody.getStatus(), is(NOT_FOUND));
    }
}

