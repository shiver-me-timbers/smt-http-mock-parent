/*
 * Copyright 2016 Karl Bennett
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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

    public ServiceServletAdaptor(Service service) {
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
