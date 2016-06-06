package shiver.me.timbers.http.mock;

import org.junit.Before;
import org.junit.Test;
import shiver.me.timbers.http.Headers;
import shiver.me.timbers.http.Request;

import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static shiver.me.timbers.data.random.RandomStrings.someString;
import static shiver.me.timbers.matchers.Matchers.hasField;
import static shiver.me.timbers.matchers.Matchers.hasFieldThat;

public class HttpMockRequestArgumentFactoryTest {

    private HttpMockRequestArgumentFactory factory;

    @Before
    public void setUp() {
        factory = new HttpMockRequestArgumentFactory();
    }

    @Test
    @SuppressWarnings("unchecked")
    public void Will_always_create_a_request_method_and_path_argument() {

        final Request request = mock(Request.class);

        final String httpMethod = someString();
        final String path = someString();

        // Given
        given(request.getMethod()).willReturn(httpMethod);
        given(request.getPath()).willReturn(path);
        given(request.hasHeaders()).willReturn(false);
        given(request.hasBody()).willReturn(false);

        // When
        final HttpMockArguments actual = factory.create(request);

        // Then
        assertThat(actual, hasField("httpMethod", httpMethod));
        assertThat(actual, hasFieldThat("types", contains(String.class)));
        assertThat(actual, hasFieldThat("values", contains(path)));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void Can_create_a_request_headers_argument() {

        final Request request = mock(Request.class);

        final String httpMethod = someString();
        final String path = someString();
        final Headers headers = mock(Headers.class);

        // Given
        given(request.getMethod()).willReturn(httpMethod);
        given(request.getPath()).willReturn(path);
        given(request.hasHeaders()).willReturn(true);
        given(request.getHeaders()).willReturn(headers);
        given(request.hasBody()).willReturn(false);

        // When
        final HttpMockArguments actual = factory.create(request);

        // Then
        assertThat(actual, hasField("httpMethod", httpMethod));
        assertThat(actual, hasFieldThat("types", contains(String.class, Headers.class)));
        assertThat(actual, hasFieldThat("values", contains(path, headers)));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void Can_create_a_request_body_argument() {

        final Request request = mock(Request.class);

        final String httpMethod = someString();
        final String path = someString();
        final String body = someString();

        // Given
        given(request.getMethod()).willReturn(httpMethod);
        given(request.getPath()).willReturn(path);
        given(request.hasHeaders()).willReturn(false);
        given(request.hasBody()).willReturn(true);
        given(request.getBodyAsString()).willReturn(body);

        // When
        final HttpMockArguments actual = factory.create(request);

        // Then
        assertThat(actual, hasField("httpMethod", httpMethod));
        assertThat(actual, hasFieldThat("types", contains(String.class, String.class)));
        assertThat(actual, hasFieldThat("values", contains(path, body)));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void Can_create_a_request_headers_and_body_argument() {

        final Request request = mock(Request.class);

        final String httpMethod = someString();
        final String path = someString();
        final Headers headers = mock(Headers.class);
        final String body = someString();

        // Given
        given(request.getMethod()).willReturn(httpMethod);
        given(request.getPath()).willReturn(path);
        given(request.hasHeaders()).willReturn(true);
        given(request.getHeaders()).willReturn(headers);
        given(request.hasBody()).willReturn(true);
        given(request.getBodyAsString()).willReturn(body);

        // When
        final HttpMockArguments actual = factory.create(request);

        // Then
        assertThat(actual, hasField("httpMethod", httpMethod));
        assertThat(actual, hasFieldThat("types", contains(String.class, Headers.class, String.class)));
        assertThat(actual, hasFieldThat("values", contains(path, headers, body)));
    }
}