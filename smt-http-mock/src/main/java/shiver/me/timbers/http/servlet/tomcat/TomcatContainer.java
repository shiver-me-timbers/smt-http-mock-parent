package shiver.me.timbers.http.servlet.tomcat;

import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;
import shiver.me.timbers.http.Container;
import shiver.me.timbers.http.Service;
import shiver.me.timbers.http.servlet.ServiceToServletConverter;

import java.util.concurrent.Callable;

/**
 * @author Karl Bennett
 */
public class TomcatContainer implements Container {

    private static final String TEMP_DIR = ".tomcat_mock";

    static {
        // Override the normal Tomcat Juli logging with slf4j.
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
    }

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final Tomcat tomcat;
    private final ServiceToServletConverter converter;
    private final Context context;
    private final FileCleaner fileCleaner;

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
            configurer.configure(tomcat, TEMP_DIR),
            new FileCleaner()
        );
    }

    TomcatContainer(Tomcat tomcat, ServiceToServletConverter converter, Context context, FileCleaner fileCleaner) {
        this.tomcat = tomcat;
        this.converter = converter;
        this.context = context;
        this.fileCleaner = fileCleaner;
    }

    @Override
    public void register(Service service) {
        tomcat.addServlet(context.getPath(), service.getName(), converter.convert(service))
            .addMapping(service.getPath());
        log.info(
            "Service ({}) registered with name ({}) and mapped to path ({}).",
            service.getClass().getName(),
            service.getName(),
            service.getPath()
        );
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
                fileCleaner.cleanUp(TEMP_DIR);
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
