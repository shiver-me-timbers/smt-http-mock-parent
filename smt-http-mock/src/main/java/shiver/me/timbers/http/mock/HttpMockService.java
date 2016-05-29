package shiver.me.timbers.http.mock;

import shiver.me.timbers.http.Request;
import shiver.me.timbers.http.Response;
import shiver.me.timbers.http.Service;

/**
 * @author Karl Bennett
 */
class HttpMockService implements Service {

    private final HttpMockHandlerChain handlerChain;
    private HttpMockHandler handler;

    HttpMockService() {
        this(new HttpMockHandlerChain());
    }

    HttpMockService(HttpMockHandlerChain handlerChain) {
        this.handlerChain = handlerChain;
    }

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
        return handlerChain.handle(handler, request);
    }

    HttpMockHandler registerHandler(HttpMockHandler handler) {
        return this.handler = handler;
    }
}
