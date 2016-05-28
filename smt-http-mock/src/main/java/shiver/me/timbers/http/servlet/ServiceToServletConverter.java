package shiver.me.timbers.http.servlet;

import shiver.me.timbers.http.Service;

import javax.servlet.Servlet;

/**
 * @author Karl Bennett
 */
public class ServiceToServletConverter {

    public Servlet convert(Service service) {
        return new ServiceServletAdaptor(service);
    }
}
