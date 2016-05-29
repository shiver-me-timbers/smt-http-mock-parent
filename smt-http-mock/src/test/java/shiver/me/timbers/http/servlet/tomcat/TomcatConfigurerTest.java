package shiver.me.timbers.http.servlet.tomcat;

import org.apache.catalina.Context;
import org.apache.catalina.Engine;
import org.apache.catalina.Host;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;
import org.junit.Test;

import static java.lang.String.format;
import static org.hamcrest.Matchers.instanceOf;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.mock;
import static shiver.me.timbers.data.random.RandomIntegers.someInteger;
import static shiver.me.timbers.data.random.RandomStrings.someString;

public class TomcatConfigurerTest {

    @Test
    public void Can_configure_a_tomcat() {

        final PortGenerator portGenerator = mock(PortGenerator.class);
        final HashGenerator hashGenerator = mock(HashGenerator.class);
        final Tomcat tomcat = mock(Tomcat.class);
        final int port = someInteger();

        final Engine engine = mock(Engine.class);
        final String engineName = someString();
        final int hash = someInteger();
        final Host host = mock(Host.class);
        final Context context = mock(Context.class);
        final Connector connector = mock(Connector.class);

        // Given
        given(portGenerator.generatePort()).willReturn(port);
        given(tomcat.getEngine()).willReturn(engine);
        given(engine.getName()).willReturn(engineName);
        given(hashGenerator.generate(tomcat)).willReturn(hash);
        given(tomcat.getHost()).willReturn(host);
        given(tomcat.addWebapp(host, "", "/")).willReturn(context);
        given(tomcat.getConnector()).willReturn(connector);

        // Given

        // When
        new TomcatConfigurer(portGenerator, hashGenerator).configure(tomcat);

        // Then
        then(engine).should().setName(format("%s%d", engineName, hash));
        then(tomcat).should().setPort(port);
        then(connector).should().setAllowTrace(true);
        then(context).should().setJarScanner((NullJarScanner) argThat(instanceOf(NullJarScanner.class)));
    }
}