package shiver.me.timbers.http.mock;

import shiver.me.timbers.http.Request;
import shiver.me.timbers.http.Response;
import shiver.me.timbers.http.Service;

/**
 * @author Karl Bennett
 */
public class HttpMockService implements Service {

    private MockHttpResponse getResponse;
    private int numberOfGetRequests;

    @Override
    public String getName() {
        return getClass().getSimpleName();
    }

    @Override
    public String getPath() {
        return "/";
    }

    @Override
    public Response call(Request request) {
        this.numberOfGetRequests++;
        return getResponse;
    }

    void setGetResponse(MockHttpResponse response) {
        getResponse = response;
    }

    int getNumberOfGetRequests() {
        return numberOfGetRequests;
    }
}
