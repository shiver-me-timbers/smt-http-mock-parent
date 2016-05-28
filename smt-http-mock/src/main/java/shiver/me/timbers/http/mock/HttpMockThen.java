package shiver.me.timbers.http.mock;

/**
 * @author Karl Bennett
 */
public class HttpMockThen {

    private final HttpMockService service;

    public HttpMockThen(HttpMockService service) {
        this.service = service;
    }

    public HttpMockVerify should() {
        return new HttpMockVerify(service);
    }
}
