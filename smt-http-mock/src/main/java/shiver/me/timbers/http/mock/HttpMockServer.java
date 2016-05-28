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

    HttpMockServer(Container container, HttpMockService service) {
        this.container = container;
        this.service = service;
        container.register(service);
        container.start();
    }

    public int getPort() {
        return container.getPort();
    }

    public HttpMockService getService() {
        return service;
    }

    public HttpGetStubbing get() {
        return new HttpGetStubbing(service);
    }

    public void stop() {
        container.stop();
    }
}
