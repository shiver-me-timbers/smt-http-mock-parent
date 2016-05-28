package shiver.me.timbers.http.mock;

/**
 * @author Karl Bennett
 */
public class HttpMock {

    public static HttpMockStub givenHttp(HttpStubbing stubbing) {
        return new HttpMockStub(stubbing);
    }

    public static MockHttpResponse status(int statusCode) {
        return new MockHttpResponse(statusCode);
    }

    public static HttpMockThen thenHttp(HttpMockServer server) {
        return new HttpMockThen(server.getService());
    }
}
