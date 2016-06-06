package shiver.me.timbers.http.mock;

import shiver.me.timbers.http.Container;
import shiver.me.timbers.http.servlet.tomcat.TomcatContainer;

/**
 * @author Karl Bennett
 */
public class HttpMockLocalServer implements HttpMockServer {

    private final Container container;
    private final HttpMockService service;

    public HttpMockLocalServer() {
        this(0);
    }

    private HttpMockLocalServer(int port) {
        this(new TomcatContainer(port, "mock"), new HttpMockService());
    }

    HttpMockLocalServer(Container container, HttpMockService service) {
        this.container = container;
        this.service = service;
        container.register(service);
        container.start();
    }

    @Override
    public void ignoreHeaders(String... names) {
        service.ignoreHeaders(names);
    }

    @Override
    public int getPort() {
        return container.getPort();
    }

    @Override
    public <T> T mock(T handler) {
        return service.registerHandler(handler);
    }

    @Override
    public void stop() {
        container.stop();
    }
}
