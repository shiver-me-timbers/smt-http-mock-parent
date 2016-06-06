package shiver.me.timbers.http.servlet.tomcat;

import org.apache.catalina.Context;
import org.apache.catalina.Engine;
import org.apache.catalina.startup.Tomcat;

import static java.lang.String.format;

/**
 * @author Karl Bennett
 */
class TomcatConfigurer {

    private final PortGenerator portGenerator;
    private final String contextPath;
    private final HashGenerator hashGenerator;

    TomcatConfigurer(int port, String contextPath) {
        this(new PortGenerator(port), contextPath, new HashGenerator());
    }

    TomcatConfigurer(PortGenerator portGenerator, String contextPath, HashGenerator hashGenerator) {
        this.portGenerator = portGenerator;
        this.contextPath = contextPath;
        this.hashGenerator = hashGenerator;
    }

    Context configure(Tomcat tomcat, String tempDir) {
        tomcat.setBaseDir(tempDir);
        tomcat.setPort(portGenerator.generatePort());
        tomcat.getConnector().setAllowTrace(true);
        final Engine engine = tomcat.getEngine();
        engine.setName(format("%s%d", engine.getName(), hashGenerator.generate(tomcat)));
        final Context context = tomcat.addWebapp(tomcat.getHost(), contextPath, "/");
        context.setJarScanner(new NullJarScanner());
        return context;
    }
}
