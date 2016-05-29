package shiver.me.timbers.http.servlet.tomcat;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.Wrapper;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import shiver.me.timbers.http.Service;
import shiver.me.timbers.http.servlet.ServiceToServletConverter;

import javax.servlet.Servlet;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.mock;
import static shiver.me.timbers.data.random.RandomIntegers.someInteger;
import static shiver.me.timbers.data.random.RandomStrings.someString;
import static shiver.me.timbers.matchers.Matchers.hasField;

public class TomcatContainerTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private Tomcat tomcat;
    private ServiceToServletConverter converter;
    private Context context;
    private TomcatContainer container;

    @Before
    public void setUp() {
        tomcat = mock(Tomcat.class);
        converter = mock(ServiceToServletConverter.class);
        context = mock(Context.class);
        container = new TomcatContainer(tomcat, converter, context);
    }

    @Test
    public void Can_create_a_tomcat_container() {

        final TomcatConfigurer configurer = mock(TomcatConfigurer.class);

        // Given
        given(configurer.configure(tomcat)).willReturn(context);

        // When
        final TomcatContainer actual = new TomcatContainer(configurer, tomcat, converter);

        // Then
        assertThat(actual, hasField("context", context));
    }

    @Test
    public void Can_register_a_service() {

        final Service service = mock(Service.class);

        final String contextPath = someString();
        final String serviceName = someString();
        final Servlet servlet = mock(Servlet.class);
        final Wrapper wrapper = mock(Wrapper.class);
        final String path = someString();

        // Given
        given(context.getName()).willReturn(contextPath);
        given(service.getName()).willReturn(serviceName);
        given(converter.convert(service)).willReturn(servlet);
        given(tomcat.addServlet(contextPath, serviceName, servlet)).willReturn(wrapper);
        given(service.getPath()).willReturn(path);

        // When
        container.register(service);

        // Then
        then(wrapper).should().addMapping(path);
    }

    @Test
    public void Can_start_the_container() throws LifecycleException {

        // When
        container.start();

        // Then
        then(tomcat).should().start();
    }

    @Test
    public void Can_fail_to_start_the_container() throws LifecycleException {

        final LifecycleException exception = new LifecycleException();

        // Given
        willThrow(exception).given(tomcat).start();
        expectedException.expect(IllegalStateException.class);
        expectedException.expectCause(is(exception));

        // When
        container.start();
    }

    @Test
    public void Can_get_the_port() {

        final Connector connector = mock(Connector.class);
        final Integer expected = someInteger();

        // Given
        given(tomcat.getConnector()).willReturn(connector);
        given(connector.getLocalPort()).willReturn(expected);

        // When
        final int actual = container.getPort();

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void Can_stop_the_container() throws LifecycleException {

        // When
        container.stop();

        // Then
        then(tomcat).should().stop();
    }

    @Test
    public void Can_fail_to_stop_the_container() throws LifecycleException {

        final LifecycleException exception = new LifecycleException();

        // Given
        willThrow(exception).given(tomcat).stop();
        expectedException.expect(IllegalStateException.class);
        expectedException.expectCause(is(exception));

        // When
        container.stop();
    }
}