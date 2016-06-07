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
    private final String tempDir;

    public TomcatContainer(int port, String contextPath) {
        this(port, contextPath, TEMP_DIR);
    }

    public TomcatContainer(int port, String contextPath, String tempDir) {
        this(new TomcatConfigurer(port, contextPath), new Tomcat(), new ServiceToServletConverter(), tempDir);
    }

    TomcatContainer(TomcatConfigurer configurer, Tomcat tomcat, ServiceToServletConverter converter, String tempDir) {
        this(tomcat, converter, configurer.configure(tomcat, tempDir), new FileCleaner(), tempDir);
    }

    TomcatContainer(
        Tomcat tomcat,
        ServiceToServletConverter converter,
        Context context,
        FileCleaner fileCleaner,
        String tempDir
    ) {
        this.tomcat = tomcat;
        this.converter = converter;
        this.context = context;
        this.fileCleaner = fileCleaner;
        this.tempDir = tempDir;
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
                fileCleaner.cleanUp(tempDir);
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
