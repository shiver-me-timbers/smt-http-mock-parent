package shiver.me.timbers.http.mock;

import org.junit.Test;

import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static shiver.me.timbers.data.random.RandomIntegers.someInteger;
import static shiver.me.timbers.http.mock.HttpMock.givenHttp;
import static shiver.me.timbers.http.mock.HttpMock.status;
import static shiver.me.timbers.http.mock.HttpMock.thenHttp;
import static shiver.me.timbers.matchers.Matchers.hasField;

public class HttpMockTest {

    @Test
    public void Can_create_a_mock_http_stub() {

        // Given
        final HttpStubbing stubbing = mock(HttpStubbing.class);

        // When
        final HttpMockStub actual = givenHttp(stubbing);

        // Then
        assertThat(actual, hasField("stubbing", stubbing));
    }

    @Test
    public void Can_create_a_status_response() {

        // Given
        final int status = someInteger();

        // When
        final MockHttpResponse actual = status(status);

        // Then
        assertThat(actual, hasField("status", status));
    }

    @Test
    public void Can_verify_a_response() {

        final HttpMockServer server = mock(HttpMockServer.class);

        final HttpMockService service = mock(HttpMockService.class);

        // Given
        given(server.getService()).willReturn(service);

        // When
        final HttpMockThen actual = thenHttp(server);

        // Then
        assertThat(actual, hasField("service", service));
    }
}