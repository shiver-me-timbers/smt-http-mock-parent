package shiver.me.timbers.http.servlet.tomcat;

import org.apache.catalina.Context;
import org.apache.catalina.Engine;
import org.apache.catalina.startup.Tomcat;
import org.slf4j.bridge.SLF4JBridgeHandler;
import shiver.me.timbers.http.Container;
import shiver.me.timbers.http.Service;
import shiver.me.timbers.http.servlet.ServiceToServletConverter;

import java.util.concurrent.Callable;

import static java.lang.String.format;

/**
 * @author Karl Bennett
 */
public class TomcatContainer implements Container {

    static {
        // Override the normal Tomcat Juli logging with slf4j.
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
    }

    private final Tomcat tomcat;
    private final ServiceToServletConverter converter;
    private final Context context;

    public TomcatContainer(int port) {
        this(new TomcatConfigurer(port), new Tomcat(), new ServiceToServletConverter());
    }

    TomcatContainer(
        TomcatConfigurer configurer,
        Tomcat tomcat,
        ServiceToServletConverter converter
    ) {
        this(
            tomcat,
            converter,
            configurer.configure(tomcat)
        );
    }

    TomcatContainer(Tomcat tomcat, ServiceToServletConverter converter, Context context) {
        this.tomcat = tomcat;
        this.converter = converter;
        this.context = context;
    }

    private static Tomcat configure(Tomcat tomcat, HashGenerator hashGenerator) {
        tomcat.setPort(0);
        tomcat.getConnector().setAllowTrace(true);
        final Engine engine = tomcat.getEngine();
        engine.setName(format("%s%d", engine.getName(), hashGenerator.generate(tomcat)));
        return tomcat;
    }

    private static Context createContext(Tomcat tomcat) {
        final Context context = tomcat.addWebapp(tomcat.getHost(), "", "/");
        context.setJarScanner(new NullJarScanner());
        return context;
    }

    @Override
    public void register(Service service) {
        // Context.getPath() doesn't seem to work, which is odd.
        tomcat.addServlet(context.getName(), service.getName(), converter.convert(service))
            .addMapping(service.getPath());
    }

    @Override
    public void start() {
        withinLifecycle(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                tomcat.start();
                return null;
            }
        });
    }

    @Override
    public int getPort() {
        return tomcat.getConnector().getLocalPort();
    }

    @Override
    public void stop() {
        withinLifecycle(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                tomcat.stop();
                return null;
            }
        });
    }

    private static void withinLifecycle(Callable<Void> callable) {
        try {
            callable.call();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}
