package shiver.me.timbers.http.mock;

import shiver.me.timbers.http.Response;

/**
 * @author Karl Bennett
 */
public class MockHttpResponse implements Response {

    private final int status;

    public MockHttpResponse(int status) {
        this.status = status;
    }

    @Override
    public int getStatus() {
        return status;
    }
}
