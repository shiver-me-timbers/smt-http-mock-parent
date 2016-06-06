package shiver.me.timbers.http.mock.integration;

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
import static shiver.me.timbers.data.random.RandomStrings.someString;
import static shiver.me.timbers.http.StatusCodes.OK;
import static shiver.me.timbers.http.mock.HttpMock.h;
import static shiver.me.timbers.http.mock.HttpMock.headers;
import static shiver.me.timbers.http.mock.integration.HttpClients.createClient;
import static shiver.me.timbers.http.mock.integration.RandomHttp.somePath;
import static shiver.me.timbers.http.mock.integration.RandomHttp.toHeaders;
import static shiver.me.timbers.http.mock.integration.RandomHttp.toMap;

public abstract class AbstractHttpMultipleRequests {

    protected abstract HttpMockServer http();

    @Test
    public void Can_mock_an_http_post_request_with_headers_and_a_body() {

        final String path = somePath();
        final String otherPath = somePath();
        final String body = someString();
        final String name1 = someAlphaString(4);
        final String name2 = someAlphaString(4);
        final String name3 = someAlphaString(4);
        final String value1 = someAlphaString(6);
        final String value2 = someAlphaString(6);
        final String value3 = someAlphaString(6);
        final MultivaluedMap<String, Object> headerMap = toMap(name1, value1, name2, value2, name3, value3);
        final HttpMockHandler handler = http().mock(mock(HttpMockHandler.class));
        final HttpMockResponse response = mock(HttpMockResponse.class);

        // Given
        given(handler.get(path)).willReturn(response);
        given(handler.get(otherPath)).willReturn(response);
        given(handler.get(path, headers(h(name1, value1), h(name2, value2), h(name3, value3)))).willReturn(response);
        given(handler.post(path, body)).willReturn(response);
        given(handler.post(path, headers(h(name1, value1), h(name2, value2), h(name3, value3)), body))
            .willReturn(response);
        given(response.getStatus()).willReturn(OK);

        // When
        final Response pathResponse = createClient(http()).path(path).request().get();
        final Response otherPathResponse = createClient(http()).path(otherPath).request().get();
        final Response pathAndHeadersResponse = createClient(http()).path(path).request().headers(headerMap).get();
        final Response pathAndBodyResponse = createClient(http()).path(path).request().post(text(body));
        final Response pathHeaders_andBodyResponse = createClient(http()).path(path).request().headers(headerMap).post(text(body));

        // Then
        then(handler).should().get(path);
        then(handler).should().get(otherPath);
        then(handler).should().get(path, toHeaders(headerMap));
        then(handler).should().post(path, body);
        then(handler).should().post(path, toHeaders(headerMap), body);
        assertThat(pathResponse.getStatus(), is(OK));
        assertThat(otherPathResponse.getStatus(), is(OK));
        assertThat(pathAndHeadersResponse.getStatus(), is(OK));
        assertThat(pathAndBodyResponse.getStatus(), is(OK));
        assertThat(pathHeaders_andBodyResponse.getStatus(), is(OK));
    }
}

