package shiver.me.timbers.http.servlet.tomcat;

import org.apache.catalina.Context;
import org.apache.catalina.Engine;
import org.apache.catalina.startup.Tomcat;

import static java.lang.String.format;

/**
 * @author Karl Bennett
 */
public class TomcatConfigurer {

    private final PortGenerator portGenerator;
    private final HashGenerator hashGenerator;

    public TomcatConfigurer(int port) {
        this(new PortGenerator(port), new HashGenerator());
    }

    public TomcatConfigurer(PortGenerator portGenerator, HashGenerator hashGenerator) {
        this.portGenerator = portGenerator;
        this.hashGenerator = hashGenerator;
    }

    public Context configure(Tomcat tomcat) {
        tomcat.setPort(portGenerator.generatePort());
        tomcat.getConnector().setAllowTrace(true);
        final Engine engine = tomcat.getEngine();
        engine.setName(format("%s%d", engine.getName(), hashGenerator.generate(tomcat)));
        final Context context = tomcat.addWebapp(tomcat.getHost(), "", "/");
        context.setJarScanner(new NullJarScanner());
        return context;
    }
}
