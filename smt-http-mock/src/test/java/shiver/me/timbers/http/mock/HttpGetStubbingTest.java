package shiver.me.timbers.http.mock;

import org.junit.Test;

import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

public class HttpGetStubbingTest {

    @Test
    public void Can_set_the_response() {

        // Given
        final HttpMockService service = mock(HttpMockService.class);
        final MockHttpResponse response = mock(MockHttpResponse.class);

        // When
        new HttpGetStubbing(service).setResponse(response);

        // Then
        then(service).should().setGetResponse(response);
    }
}