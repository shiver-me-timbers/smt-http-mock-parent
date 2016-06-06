package shiver.me.timbers.http.mock.integration;

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

import javax.ws.rs.core.Response;

import static javax.ws.rs.client.Entity.text;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyString;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static shiver.me.timbers.http.Methods.PATCH;
import static shiver.me.timbers.http.StatusCodes.NOT_FOUND;
import static shiver.me.timbers.http.StatusCodes.OK;
import static shiver.me.timbers.http.mock.integration.CustomHttpMethodHandler.CUSTOM;
import static shiver.me.timbers.http.mock.integration.HttpClients.createClient;
import static shiver.me.timbers.http.mock.integration.RandomHttp.somePath;

public abstract class AbstractHttpRequest {

    protected abstract HttpMockServer http();

    @Test
    public void Can_mock_an_http_get_request() {

        final String path = somePath();
        final String otherPath = somePath();
        final HttpMockGET handler = http().mock(mock(HttpMockGET.class));
        final HttpMockResponse response = mock(HttpMockResponse.class);

        // Given
        given(handler.get(path)).willReturn(response);
        given(response.getStatus()).willReturn(OK);

        // When
        final Response ok = createClient(http()).path(path).request().get();
        final Response notFound = createClient(http()).path(otherPath).request().get();

        // Then
        then(handler).should().get(path);
        then(handler).should().get(otherPath);
        verifyNoMoreInteractions(handler);
        assertThat(ok.getStatus(), is(OK));
        assertThat(notFound.getStatus(), is(NOT_FOUND));
    }

    @Test
    public void Can_mock_an_http_post_request() {

        final String path = somePath();
        final String otherPath = somePath();
        final HttpMockPOST handler = http().mock(mock(HttpMockPOST.class));
        final HttpMockResponse response = mock(HttpMockResponse.class);

        // Given
        given(handler.post(path)).willReturn(response);
        given(response.getStatus()).willReturn(OK);

        // When
        final Response actual = createClient(http()).path(path).request().post(text(null), Response.class);
        final Response notFound = createClient(http()).path(otherPath).request().post(text(null), Response.class);

        // Then
        then(handler).should().post(path);
        then(handler).should().post(otherPath);
        verifyNoMoreInteractions(handler);
        assertThat(actual.getStatus(), is(OK));
        assertThat(notFound.getStatus(), is(NOT_FOUND));
    }

    @Test
    public void Can_mock_an_http_put_request() {

        final String path = somePath();
        final String otherPath = somePath();
        final HttpMockPUT handler = http().mock(mock(HttpMockPUT.class));
        final HttpMockResponse response = mock(HttpMockResponse.class);

        // Given
        given(handler.put(path)).willReturn(response);
        given(response.getStatus()).willReturn(OK);

        // When
        final Response actual = createClient(http()).path(path).request().put(text(""));
        final Response notFound = createClient(http()).path(otherPath).request().put(text(""));

        // Then
        then(handler).should().put(path);
        then(handler).should().put(otherPath);
        verifyNoMoreInteractions(handler);
        assertThat(actual.getStatus(), is(OK));
        assertThat(notFound.getStatus(), is(NOT_FOUND));
    }

    @Test
    public void Can_mock_an_http_patch_request() {

        final String path = somePath();
        final String otherPath = somePath();
        final HttpMockPATCH handler = http().mock(mock(HttpMockPATCH.class));
        final HttpMockResponse response = mock(HttpMockResponse.class);

        // Given
        given(handler.patch(path)).willReturn(response);
        given(response.getStatus()).willReturn(OK);

        // When
        final Response actual = createClient(http()).path(path).request().method(PATCH);
        final Response notFound = createClient(http()).path(otherPath).request().method(PATCH);

        // Then
        then(handler).should().patch(path);
        then(handler).should().patch(otherPath);
        verifyNoMoreInteractions(handler);
        assertThat(actual.getStatus(), is(OK));
        assertThat(notFound.getStatus(), is(NOT_FOUND));
    }

    @Test
    public void Can_mock_an_http_delete_request() {

        final String path = somePath();
        final String otherPath = somePath();
        final HttpMockDELETE handler = http().mock(mock(HttpMockDELETE.class));
        final HttpMockResponse response = mock(HttpMockResponse.class);

        // Given
        given(handler.delete(path)).willReturn(response);
        given(response.getStatus()).willReturn(OK);

        // When
        final Response actual = createClient(http()).path(path).request().delete();
        final Response notFound = createClient(http()).path(otherPath).request().delete();

        // Then
        then(handler).should().delete(path);
        then(handler).should().delete(otherPath);
        verifyNoMoreInteractions(handler);
        assertThat(actual.getStatus(), is(OK));
        assertThat(notFound.getStatus(), is(NOT_FOUND));
    }

    @Test
    public void Can_mock_an_http_options_request() {

        final String path = somePath();
        final String otherPath = somePath();
        final HttpMockOPTIONS handler = http().mock(mock(HttpMockOPTIONS.class));
        final HttpMockResponse response = mock(HttpMockResponse.class);

        // Given
        given(handler.options(path)).willReturn(response);
        given(response.getStatus()).willReturn(OK);

        // When
        final Response actual = createClient(http()).path(path).request().options();
        final Response notFound = createClient(http()).path(otherPath).request().options();

        // Then
        then(handler).should().options(path);
        then(handler).should().options(otherPath);
        verifyNoMoreInteractions(handler);
        assertThat(actual.getStatus(), is(OK));
        assertThat(notFound.getStatus(), is(NOT_FOUND));
    }

    @Test
    public void Can_mock_an_http_head_request() {

        final String path = somePath();
        final String otherPath = somePath();
        final HttpMockHEAD handler = http().mock(mock(HttpMockHEAD.class));
        final HttpMockResponse response = mock(HttpMockResponse.class);

        // Given
        given(handler.head(path)).willReturn(response);
        given(response.getStatus()).willReturn(OK);

        // When
        final Response actual = createClient(http()).path(path).request().head();
        final Response notFound = createClient(http()).path(otherPath).request().head();

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
        final HttpMockTRACE handler = http().mock(mock(HttpMockTRACE.class));
        final HttpMockResponse response = mock(HttpMockResponse.class);

        // Given
        given(handler.trace(path)).willReturn(response);
        given(response.getStatus()).willReturn(OK);

        // When
        final Response actual = createClient(http()).path(path).request().trace();
        final Response notFound = createClient(http()).path(otherPath).request().trace();

        // Then
        then(handler).should().trace(path);
        then(handler).should().trace(otherPath);
        verifyNoMoreInteractions(handler);
        assertThat(actual.getStatus(), is(OK));
        assertThat(notFound.getStatus(), is(NOT_FOUND));
    }

    @Test
    public void Can_mock_a_non_standard_http_request() {

        final String path = somePath();
        final String otherPath = somePath();
        final CustomHttpMethodHandler handler = http().mock(mock(CustomHttpMethodHandler.class));
        final HttpMockResponse response = mock(HttpMockResponse.class);

        // Given
        given(handler.custom(path)).willReturn(response);
        given(response.getStatus()).willReturn(OK);

        // When
        final Response actual = createClient(http()).path(path).request().method(CUSTOM);
        final Response notFound = createClient(http()).path(otherPath).request().method(CUSTOM);

        // Then
        then(handler).should().custom(path);
        then(handler).should().custom(otherPath);
        verifyNoMoreInteractions(handler);
        assertThat(actual.getStatus(), is(OK));
        assertThat(notFound.getStatus(), is(NOT_FOUND));
    }
}

