package shiver.me.timbers.http.mock;

/**
 * @author Karl Bennett
 */
class HttpGetStubbing implements HttpStubbing {

    private final HttpMockService service;

    HttpGetStubbing(HttpMockService service) {
        this.service = service;
    }

    @Override
    public void setResponse(MockHttpResponse response) {
        service.setGetResponse(response);
    }
}
