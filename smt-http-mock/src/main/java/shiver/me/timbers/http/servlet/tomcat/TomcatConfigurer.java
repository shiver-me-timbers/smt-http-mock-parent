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
