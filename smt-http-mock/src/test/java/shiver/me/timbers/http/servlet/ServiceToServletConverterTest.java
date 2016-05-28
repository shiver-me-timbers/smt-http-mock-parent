package shiver.me.timbers.http.servlet;

import org.junit.Test;
import shiver.me.timbers.http.Service;

import javax.servlet.Servlet;

import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static shiver.me.timbers.matchers.Matchers.hasField;

public class ServiceToServletConverterTest {

    @Test
    public void Can_convert_a_service_into_a_servlet() {

        // Given
        final Service service = mock(Service.class);

        // When
        final Servlet actual = new ServiceToServletConverter().convert(service);

        // Then
        assertThat(actual, hasField("service", service));
    }
}