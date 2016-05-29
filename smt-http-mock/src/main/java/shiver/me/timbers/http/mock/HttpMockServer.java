package shiver.me.timbers.http.mock;

import shiver.me.timbers.http.Container;
import shiver.me.timbers.http.servlet.tomcat.TomcatContainer;

/**
 * @author Karl Bennett
 */
public class HttpMockServer {

    private final Container container;
    private final HttpMockService service;

    public HttpMockServer() {
        this(new TomcatContainer(), new HttpMockService());
    }

    public HttpMockServer(Container container, HttpMockService service) {
        this.container = container;
        this.service = service;
        container.register(service);
        container.start();
    }

    public int getPort() {
        return container.getPort();
    }

    public HttpMockHandler mock(HttpMockHandler handler) {
        return service.registerHandler(handler);
    }

    public void stop() {
        container.stop();
    }
}
