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

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static shiver.me.timbers.data.random.RandomStrings.someAlphaString;
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
        http.ignoreHeaders("Host", "Connection", "User-Agent", "Accept");
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
        final Response notFound = createClient(http).path(otherPath).request().get();

        // Then
        final Headers headers = headers(h(name1, value1), h(name2, value2), h(name3, value3));
        then(handler).should().get(path, headers);
        assertThat(ok.getStatus(), is(OK));
        assertThat(notFound.getStatus(), is(NOT_FOUND));
    }
}

