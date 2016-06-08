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

package shiver.me.timbers.http.servlet.tomcat;

import org.apache.catalina.Host;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.JarScanner;

import javax.servlet.Servlet;

/**
 * @author Karl Bennett
 */
class Tomcat8 implements CommonTomcat<Host, JarScanner, LifecycleException> {

    private final Tomcat tomcat;

    Tomcat8(Tomcat tomcat) {
        this.tomcat = tomcat;
    }

    @Override
    public CommonEngine getEngine() {
        return new Tomcat8Engine(tomcat.getEngine());
    }

    @Override
    public Host getHost() {
        return tomcat.getHost();
    }

    @Override
    public CommonContext<JarScanner> addWebApp(Host host, String contextPath, String docBase) {
        return new Tomcat8Context(tomcat.addWebapp(host, contextPath, docBase));
    }

    @Override
    public CommonConnector getConnector() {
        return new Tomcat8Connector(tomcat.getConnector());
    }

    @Override
    public void setBaseDir(String baseDir) {
        tomcat.setBaseDir(baseDir);
    }

    @Override
    public void setPort(int port) {
        tomcat.setPort(port);
    }

    @Override
    public CommonWrapper addServlet(String contextPath, String name, Servlet servlet) {
        return new Tomcat8Wrapper(tomcat.addServlet(contextPath, name, servlet));
    }

    @Override
    public void start() throws LifecycleException {
        tomcat.start();
    }

    @Override
    public void stop() throws LifecycleException {
        tomcat.stop();
    }
}
