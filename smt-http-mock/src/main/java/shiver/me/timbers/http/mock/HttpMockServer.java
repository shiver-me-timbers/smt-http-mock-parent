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
        this(0);
    }

    private HttpMockServer(int port) {
        this(new TomcatContainer(port), new HttpMockService());
    }

    HttpMockServer(Container container, HttpMockService service) {
        this.container = container;
        this.service = service;
        container.register(service);
        container.start();
    }

    public void ignoreHeaders(String... names) {
        service.ignoreHeaders(names);
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
