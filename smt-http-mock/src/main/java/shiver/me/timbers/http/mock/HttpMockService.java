package shiver.me.timbers.http.mock;

import shiver.me.timbers.http.Request;
import shiver.me.timbers.http.Response;
import shiver.me.timbers.http.Service;

/**
 * @author Karl Bennett
 */
public class HttpMockService implements Service {

    private HttpMockHandler handler;

    @Override
    public String getName() {
        return getClass().getSimpleName();
    }

    @Override
    public String getPath() {
        return "/mock";
    }

    @Override
    public Response call(Request request) {
        return handler.get();
    }

    public HttpMockHandler registerHandler(HttpMockHandler handler) {
        return this.handler = handler;
    }
}
