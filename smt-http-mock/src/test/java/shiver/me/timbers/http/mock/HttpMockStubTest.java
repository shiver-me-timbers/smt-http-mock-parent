package shiver.me.timbers.http.mock;

import org.junit.Test;

import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

public class HttpMockStubTest {

    @Test
    public void Can_setup_an_http_response() {

        // Given
        final HttpStubbing stubbing = mock(HttpStubbing.class);
        final MockHttpResponse response = mock(MockHttpResponse.class);

        // When
        new HttpMockStub(stubbing).willRespond(response);

        // Then
        then(stubbing).should().setResponse(response);
    }
}