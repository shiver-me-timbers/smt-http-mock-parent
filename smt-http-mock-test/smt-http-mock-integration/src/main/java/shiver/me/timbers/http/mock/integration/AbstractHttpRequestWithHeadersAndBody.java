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
import static shiver.me.timbers.http.mock.integration.JavaVersion.assumeJava8;
import static shiver.me.timbers.http.mock.integration.RandomHttp.someHeaders;
import static shiver.me.timbers.http.mock.integration.RandomHttp.somePath;
import static shiver.me.timbers.http.mock.integration.RandomHttp.toHeaders;
import static shiver.me.timbers.http.mock.integration.RandomHttp.toMap;

public abstract class AbstractHttpRequestWithHeadersAndBody {

    private String path;
    private String otherPath;
    private String body;
    private String otherBody;
    private String name1;
    private String name2;
    private String name3;
    private String value1;
    private String value2;
    private String value3;
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
        otherPath = somePath();
        body = someString(13);
        otherBody = someString(13);
        name1 = someAlphaString(4);
        name2 = someAlphaString(4);
        name3 = someAlphaString(4);
        value1 = someAlphaString(6);
        value2 = someAlphaString(6);
        value3 = someAlphaString(6);
        headerMap = toMap(name1, value1, name2, value2, name3, value3);
        otherHeaderMap = someHeaders();
    }

    @Test
    public void Can_mock_an_http_post_request_with_headers_and_a_body() {

        final HttpMockPOST handler = http().mock(mock(HttpMockPOST.class));
        final HttpMockResponse response = mock(HttpMockResponse.class);

        // Given
        given(handler.post(path, headers(h(name1, value1), h(name2, value2), h(name3, value3)), body))
            .willReturn(response);
        given(response.getStatus()).willReturn(OK);

        // When
        final Response ok = createClient(http()).path(path).request().headers(headerMap).post(text(body));
        final Response invalidPath = createClient(http()).path(otherPath).request().headers(headerMap).post(text(body));
        final Response invalidHeaders = createClient(http()).path(path).request().headers(otherHeaderMap).post(text(body));
        final Response invalidBody = createClient(http()).path(path).request().headers(headerMap).post(text(otherBody));

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

        final HttpMockPUT handler = http().mock(mock(HttpMockPUT.class));
        final HttpMockResponse response = mock(HttpMockResponse.class);

        // Given
        given(handler.put(path, headers(h(name1, value1), h(name2, value2), h(name3, value3)), body))
            .willReturn(response);
        given(response.getStatus()).willReturn(OK);

        // When
        final Response ok = createClient(http()).path(path).request().headers(headerMap).put(text(body));
        final Response invalidPath = createClient(http()).path(otherPath).request().headers(headerMap).put(text(body));
        final Response invalidHeaders = createClient(http()).path(path).request().headers(otherHeaderMap).put(text(body));
        final Response invalidBody = createClient(http()).path(path).request().headers(headerMap).put(text(otherBody));

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
        assumeJava8();

        final HttpMockPATCH handler = http().mock(mock(HttpMockPATCH.class));
        final HttpMockResponse response = mock(HttpMockResponse.class);

        // Given
        given(handler.patch(path, headers(h(name1, value1), h(name2, value2), h(name3, value3)), body))
            .willReturn(response);
        given(response.getStatus()).willReturn(OK);

        // When
        final Response ok = createClient(http()).path(path).request().headers(headerMap).method(PATCH, text(body));
        final Response invalidPath = createClient(http()).path(otherPath).request().headers(headerMap).method(PATCH, text(body));
        final Response invalidHeaders = createClient(http()).path(path).request().headers(otherHeaderMap).method(PATCH, text(body));
        final Response invalidBody = createClient(http()).path(path).request().headers(headerMap).method(PATCH, text(otherBody));

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
        assumeJava8();

        final CustomHttpMethodHandler handler = http().mock(mock(CustomHttpMethodHandler.class));
        final HttpMockResponse response = mock(HttpMockResponse.class);

        // Given
        given(handler.custom(path, headers(h(name1, value1), h(name2, value2), h(name3, value3)), body))
            .willReturn(response);
        given(response.getStatus()).willReturn(OK);

        // When
        final Response ok = createClient(http()).path(path).request().headers(headerMap).method(CUSTOM, text(body));
        final Response invalidPath = createClient(http()).path(otherPath).request().headers(headerMap).method(CUSTOM, text(body));
        final Response invalidHeaders = createClient(http()).path(path).request().headers(otherHeaderMap).method(CUSTOM, text(body));
        final Response invalidBody = createClient(http()).path(path).request().headers(headerMap).method(CUSTOM, text(otherBody));

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

