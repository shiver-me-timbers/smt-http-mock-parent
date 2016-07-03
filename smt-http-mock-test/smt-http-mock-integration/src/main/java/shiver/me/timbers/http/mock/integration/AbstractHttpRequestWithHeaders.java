/*
 * Copyright 2016 Karl Bennett
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package shiver.me.timbers.http.mock.integration;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import shiver.me.timbers.http.mock.HttpMockDELETE;
import shiver.me.timbers.http.mock.HttpMockGET;
import shiver.me.timbers.http.mock.HttpMockHEAD;
import shiver.me.timbers.http.mock.HttpMockOPTIONS;
import shiver.me.timbers.http.mock.HttpMockPATCH;
import shiver.me.timbers.http.mock.HttpMockPOST;
import shiver.me.timbers.http.mock.HttpMockPUT;
import shiver.me.timbers.http.mock.HttpMockResponse;
import shiver.me.timbers.http.mock.HttpMockServer;
import shiver.me.timbers.http.mock.HttpMockTRACE;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import static java.util.Collections.singletonList;
import static javax.ws.rs.client.Entity.text;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.hamcrest.Matchers.hasEntry;
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

public abstract class AbstractHttpRequestWithHeaders {


    private String path;
    private String name1;
    private String name2;
    private String name3;
    private String name4;
    private String value1;
    private String value2;
    private String value3;
    private String value4;
    private MultivaluedMap<String, Object> headerMap;
    private MultivaluedMap<String, Object> otherHeaderMap;

    protected abstract HttpMockServer http();

    @Before
    public void httpSetUp() {
        http().ignoreHeaders("Host", "Connection", "User-Agent", "Accept", "Content-Type", "Content-Length");
    }

    @After
    public void httpTearDown() {
        http().mock(null);
    }

    @Before
    public void setup() {
        path = somePath();
        name1 = someAlphaString(4);
        name2 = someAlphaString(4);
        name3 = someAlphaString(4);
        name4 = someAlphaString(4);
        value1 = someAlphaString(6);
        value2 = someAlphaString(6);
        value3 = someAlphaString(6);
        value4 = someAlphaString(6);
        headerMap = toMap(name1, value1, name2, value2, name3, value3);
        otherHeaderMap = someHeaders();
    }

    @Test
    public void Can_mock_an_http_get_request_with_headers() {

        final HttpMockGET handler = http().mock(mock(HttpMockGET.class));
        final HttpMockResponse response = mock(HttpMockResponse.class);

        // Given
        given(handler.get(path, headers(h(name1, value1), h(name2, value2), h(name3, value3)))).willReturn(response);
        given(response.getStatus()).willReturn(OK);
        given(response.getHeaders()).willReturn(headers(h(name4, value4)));

        // When
        final Response ok = createClient(http()).path(path).request().headers(headerMap).get();
        final Response notFound = createClient(http()).path(path).request().headers(otherHeaderMap).get();

        // Then
        then(handler).should().get(path, toHeaders(headerMap));
        then(handler).should().get(path, toHeaders(otherHeaderMap));
        assertThat(ok.getStatus(), is(OK));
        assertThat(ok.getHeaders(), hasEntry(equalToIgnoringCase(name4), is(singletonList((Object) value4))));
        assertThat(notFound.getStatus(), is(NOT_FOUND));
    }

    @Test
    public void Can_mock_an_http_post_request_with_headers() {

        final HttpMockPOST handler = http().mock(mock(HttpMockPOST.class));
        final HttpMockResponse response = mock(HttpMockResponse.class);

        // Given
        given(handler.post(path, headers(h(name1, value1), h(name2, value2), h(name3, value3)))).willReturn(response);
        given(response.getStatus()).willReturn(OK);

        // When
        final Response ok = createClient(http()).path(path).request().headers(headerMap).post(text(null), Response.class);
        final Response notFound = createClient(http()).path(path).request().headers(otherHeaderMap).post(text(null), Response.class);

        // Then
        then(handler).should().post(path, toHeaders(headerMap));
        then(handler).should().post(path, toHeaders(otherHeaderMap));
        assertThat(ok.getStatus(), is(OK));
        assertThat(notFound.getStatus(), is(NOT_FOUND));
    }

    @Test
    public void Can_mock_an_http_put_request_with_headers() {

        final HttpMockPUT handler = http().mock(mock(HttpMockPUT.class));
        final HttpMockResponse response = mock(HttpMockResponse.class);

        // Given
        given(handler.put(path, headers(h(name1, value1), h(name2, value2), h(name3, value3)))).willReturn(response);
        given(response.getStatus()).willReturn(OK);

        // When
        final Response ok = createClient(http()).path(path).request().headers(headerMap).put(text(""));
        final Response notFound = createClient(http()).path(path).request().headers(otherHeaderMap).put(text(""));

        // Then
        then(handler).should().put(path, toHeaders(headerMap));
        then(handler).should().put(path, toHeaders(otherHeaderMap));
        assertThat(ok.getStatus(), is(OK));
        assertThat(notFound.getStatus(), is(NOT_FOUND));
    }

    @Test
    public void Can_mock_an_http_patch_request_with_headers() {

        final HttpMockPATCH handler = http().mock(mock(HttpMockPATCH.class));
        final HttpMockResponse response = mock(HttpMockResponse.class);

        // Given
        given(handler.patch(path, headers(h(name1, value1), h(name2, value2), h(name3, value3)))).willReturn(response);
        given(response.getStatus()).willReturn(OK);

        // When
        final Response ok = createClient(http()).path(path).request().headers(headerMap).method(PATCH);
        final Response notFound = createClient(http()).path(path).request().headers(otherHeaderMap).method(PATCH);

        // Then
        then(handler).should().patch(path, toHeaders(headerMap));
        then(handler).should().patch(path, toHeaders(otherHeaderMap));
        assertThat(ok.getStatus(), is(OK));
        assertThat(notFound.getStatus(), is(NOT_FOUND));
    }

    @Test
    public void Can_mock_an_http_delete_request_with_headers() {

        final HttpMockDELETE handler = http().mock(mock(HttpMockDELETE.class));
        final HttpMockResponse response = mock(HttpMockResponse.class);

        // Given
        given(handler.delete(path, headers(h(name1, value1), h(name2, value2), h(name3, value3)))).willReturn(response);
        given(response.getStatus()).willReturn(OK);

        // When
        final Response ok = createClient(http()).path(path).request().headers(headerMap).delete();
        final Response notFound = createClient(http()).path(path).request().headers(otherHeaderMap).delete();

        // Then
        then(handler).should().delete(path, toHeaders(headerMap));
        then(handler).should().delete(path, toHeaders(otherHeaderMap));
        assertThat(ok.getStatus(), is(OK));
        assertThat(notFound.getStatus(), is(NOT_FOUND));
    }

    @Test
    public void Can_mock_an_http_options_request_with_headers() {

        final HttpMockOPTIONS handler = http().mock(mock(HttpMockOPTIONS.class));
        final HttpMockResponse response = mock(HttpMockResponse.class);

        // Given
        given(handler.options(path, headers(h(name1, value1), h(name2, value2), h(name3, value3)))).willReturn(response);
        given(response.getStatus()).willReturn(OK);

        // When
        final Response ok = createClient(http()).path(path).request().headers(headerMap).options();
        final Response notFound = createClient(http()).path(path).request().headers(otherHeaderMap).options();

        // Then
        then(handler).should().options(path, toHeaders(headerMap));
        then(handler).should().options(path, toHeaders(otherHeaderMap));
        assertThat(ok.getStatus(), is(OK));
        assertThat(notFound.getStatus(), is(NOT_FOUND));
    }

    @Test
    public void Can_mock_an_http_head_request_with_headers() {

        final HttpMockHEAD handler = http().mock(mock(HttpMockHEAD.class));
        final HttpMockResponse response = mock(HttpMockResponse.class);

        // Given
        given(handler.head(path, headers(h(name1, value1), h(name2, value2), h(name3, value3)))).willReturn(response);
        given(response.getStatus()).willReturn(OK);

        // When
        final Response ok = createClient(http()).path(path).request().headers(headerMap).head();
        final Response notFound = createClient(http()).path(path).request().headers(otherHeaderMap).head();

        // Then
        then(handler).should().head(path, toHeaders(headerMap));
        then(handler).should().head(path, toHeaders(otherHeaderMap));
        assertThat(ok.getStatus(), is(OK));
        assertThat(notFound.getStatus(), is(NOT_FOUND));
    }

    @Test
    public void Can_mock_an_http_trace_request_with_headers() {

        final HttpMockTRACE handler = http().mock(mock(HttpMockTRACE.class));
        final HttpMockResponse response = mock(HttpMockResponse.class);

        // Given
        given(handler.trace(path, headers(h(name1, value1), h(name2, value2), h(name3, value3)))).willReturn(response);
        given(response.getStatus()).willReturn(OK);

        // When
        final Response ok = createClient(http()).path(path).request().headers(headerMap).trace();
        final Response notFound = createClient(http()).path(path).request().headers(otherHeaderMap).trace();

        // Then
        then(handler).should().trace(path, toHeaders(headerMap));
        then(handler).should().trace(path, toHeaders(otherHeaderMap));
        assertThat(ok.getStatus(), is(OK));
        assertThat(notFound.getStatus(), is(NOT_FOUND));
    }

    @Test
    public void Can_mock_an_http_non_standard_request_with_headers() {

        final CustomHttpMethodHandler handler = http().mock(mock(CustomHttpMethodHandler.class));
        final HttpMockResponse response = mock(HttpMockResponse.class);

        // Given
        given(handler.custom(path, headers(h(name1, value1), h(name2, value2), h(name3, value3))))
            .willReturn(response);
        given(response.getStatus()).willReturn(OK);

        // When
        final Response ok = createClient(http()).path(path).request().headers(headerMap).method(CUSTOM);
        final Response notFound = createClient(http()).path(path).request().headers(otherHeaderMap).method(CUSTOM);

        // Then
        then(handler).should().custom(path, toHeaders(headerMap));
        then(handler).should().custom(path, toHeaders(otherHeaderMap));
        assertThat(ok.getStatus(), is(OK));
        assertThat(notFound.getStatus(), is(NOT_FOUND));
    }
}

