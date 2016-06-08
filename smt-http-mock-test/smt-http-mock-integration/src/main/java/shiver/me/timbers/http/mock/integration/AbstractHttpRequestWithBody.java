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

import org.junit.Before;
import org.junit.Test;
import shiver.me.timbers.http.mock.HttpMockPATCH;
import shiver.me.timbers.http.mock.HttpMockPOST;
import shiver.me.timbers.http.mock.HttpMockPUT;
import shiver.me.timbers.http.mock.HttpMockResponse;
import shiver.me.timbers.http.mock.HttpMockServer;

import javax.ws.rs.core.Response;

import static javax.ws.rs.client.Entity.text;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static shiver.me.timbers.data.random.RandomStrings.someString;
import static shiver.me.timbers.http.Methods.PATCH;
import static shiver.me.timbers.http.StatusCodes.NOT_FOUND;
import static shiver.me.timbers.http.StatusCodes.OK;
import static shiver.me.timbers.http.mock.integration.CustomHttpMethodHandler.CUSTOM;
import static shiver.me.timbers.http.mock.integration.HttpClients.createClient;
import static shiver.me.timbers.http.mock.integration.JavaVersion.assumeJava8;
import static shiver.me.timbers.http.mock.integration.RandomHttp.somePath;

public abstract class AbstractHttpRequestWithBody {

    private String path;
    private String body;
    private String otherBody;

    protected abstract HttpMockServer http();

    @Before
    public void setup() {
        path = somePath();
        body = someString(13);
        otherBody = someString(13);
    }

    @Test
    public void Can_mock_an_http_post_request_with_a_body() {

        final HttpMockPOST handler = http().mock(mock(HttpMockPOST.class));
        final HttpMockResponse response = mock(HttpMockResponse.class);

        // Given
        given(handler.post(path, body)).willReturn(response);
        given(response.getStatus()).willReturn(OK);

        // When
        final Response ok = createClient(http()).path(path).request().post(text(body));
        final Response notFound = createClient(http()).path(path).request().post(text(otherBody));

        // Then
        then(handler).should().post(path, body);
        then(handler).should().post(path, otherBody);
        assertThat(ok.getStatus(), is(OK));
        assertThat(notFound.getStatus(), is(NOT_FOUND));
    }

    @Test
    public void Can_mock_an_http_put_request_with_a_body() {

        final HttpMockPUT handler = http().mock(mock(HttpMockPUT.class));
        final HttpMockResponse response = mock(HttpMockResponse.class);

        // Given
        given(handler.put(path, body)).willReturn(response);
        given(response.getStatus()).willReturn(OK);

        // When
        final Response ok = createClient(http()).path(path).request().put(text(body));
        final Response notFound = createClient(http()).path(path).request().put(text(otherBody));

        // Then
        then(handler).should().put(path, body);
        then(handler).should().put(path, otherBody);
        assertThat(ok.getStatus(), is(OK));
        assertThat(notFound.getStatus(), is(NOT_FOUND));
    }

    @Test
    public void Can_mock_an_http_patch_request_with_a_body() {
        assumeJava8();

        final HttpMockPATCH handler = http().mock(mock(HttpMockPATCH.class));
        final HttpMockResponse response = mock(HttpMockResponse.class);

        // Given
        given(handler.patch(path, body)).willReturn(response);
        given(response.getStatus()).willReturn(OK);

        // When
        final Response ok = createClient(http()).path(path).request().method(PATCH, text(body));
        final Response notFound = createClient(http()).path(path).request().method(PATCH, text(otherBody));

        // Then
        then(handler).should().patch(path, body);
        then(handler).should().patch(path, otherBody);
        assertThat(ok.getStatus(), is(OK));
        assertThat(notFound.getStatus(), is(NOT_FOUND));
    }

    @Test
    public void Can_mock_a_non_standard_http_request_with_a_body() {
        assumeJava8();

        final CustomHttpMethodHandler handler = http().mock(mock(CustomHttpMethodHandler.class));
        final HttpMockResponse response = mock(HttpMockResponse.class);

        // Given
        given(handler.custom(path, body)).willReturn(response);
        given(response.getStatus()).willReturn(OK);

        // When
        final Response ok = createClient(http()).path(path).request().method(CUSTOM, text(body));
        final Response notFound = createClient(http()).path(path).request().method(CUSTOM, text(otherBody));

        // Then
        then(handler).should().custom(path, body);
        then(handler).should().custom(path, otherBody);
        assertThat(ok.getStatus(), is(OK));
        assertThat(notFound.getStatus(), is(NOT_FOUND));
    }
}

