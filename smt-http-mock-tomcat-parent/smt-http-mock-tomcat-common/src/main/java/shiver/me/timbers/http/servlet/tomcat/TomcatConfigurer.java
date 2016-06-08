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

import static java.lang.String.format;

/**
 * @author Karl Bennett
 */
class TomcatConfigurer<H, J, E extends Exception> {

    private final PortGenerator portGenerator;
    private final String contextPath;
    private final HashGenerator hashGenerator;
    private final JarScannerFactory<J> jarScannerFactory;

    TomcatConfigurer(int port, String contextPath, JarScannerFactory<J> jarScannerFactory) {
        this(new PortGenerator(port), contextPath, new HashGenerator(), jarScannerFactory);
    }

    TomcatConfigurer(
        PortGenerator portGenerator,
        String contextPath,
        HashGenerator hashGenerator,
        JarScannerFactory<J> jarScannerFactory
    ) {
        this.portGenerator = portGenerator;
        this.contextPath = contextPath;
        this.hashGenerator = hashGenerator;
        this.jarScannerFactory = jarScannerFactory;
    }

    CommonContext<J> configure(CommonTomcat<H, J, E> tomcat, String tempDir) {
        tomcat.setBaseDir(tempDir);
        tomcat.setPort(portGenerator.generatePort());
        tomcat.getConnector().setAllowTrace(true);
        final CommonEngine engine = tomcat.getEngine();
        engine.setName(format("%s%d", engine.getName(), hashGenerator.generate(tomcat)));
        final CommonContext<J> context = tomcat.addWebApp(tomcat.getHost(), contextPath, "/");
        context.setJarScanner(jarScannerFactory.create());
        return context;
    }
}
