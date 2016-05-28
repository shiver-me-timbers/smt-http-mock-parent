package shiver.me.timbers.http.mock;

/**
 * @author Karl Bennett
 */
public class HttpMockStub {

    private final HttpStubbing stubbing;

    HttpMockStub(HttpStubbing stubbing) {
        this.stubbing = stubbing;
    }

    public void willRespond(MockHttpResponse response) {
        stubbing.setResponse(response);
    }
}
