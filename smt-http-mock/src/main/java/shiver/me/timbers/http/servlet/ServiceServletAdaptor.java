package shiver.me.timbers.http.servlet;

import shiver.me.timbers.http.Service;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Karl Bennett
 */
public class ServiceServletAdaptor extends GenericServlet {

    private final Service service;
    private final ServletResponseWriter responseWriter;

    ServiceServletAdaptor(Service service) {
        this(service, new ServletResponseWriter());
    }

    ServiceServletAdaptor(Service service, ServletResponseWriter responseWriter) {
        this.service = service;
        this.responseWriter = responseWriter;
    }

    @Override
    public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
        responseWriter.write(
            (HttpServletResponse) response,
            service.call(new ServletRequestAdaptor((HttpServletRequest) request))
        );
    }
}
