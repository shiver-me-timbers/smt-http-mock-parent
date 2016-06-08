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

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;

import static java.lang.String.format;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static shiver.me.timbers.data.random.RandomIntegers.someInteger;
import static shiver.me.timbers.data.random.RandomStrings.someString;

public class TomcatConfigurerTest {

    private PortGenerator portGenerator;
    private String contextPath;
    private HashGenerator hashGenerator;
    private JarScannerFactory<Object> jarScannerFactory;
    private TomcatConfigurer<Object, Object, RuntimeException> configurer;

    @Before
    @SuppressWarnings("unchecked")
    public void setUp() {
        portGenerator = mock(PortGenerator.class);
        contextPath = someString();
        hashGenerator = mock(HashGenerator.class);
        jarScannerFactory = mock(JarScannerFactory.class);
        configurer = new TomcatConfigurer<>(portGenerator, contextPath, hashGenerator, jarScannerFactory);
    }

    @Test
    public void Create_for_coverage() {
        new TomcatConfigurer<>(someInteger(), contextPath, jarScannerFactory);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void Can_configure_a_new_tomcat() {

        final CommonTomcat<Object, Object, RuntimeException> tomcat = mock(CommonTomcat.class);
        final String baseDir = someString();

        final int port = someInteger();
        final CommonEngine engine = mock(CommonEngine.class);
        final String engineName = someString();
        final int hash = someInteger();
        final Object host = new Object();
        final CommonContext<Object> context = mock(CommonContext.class);
        final CommonConnector connector = mock(CommonConnector.class);
        final Object jarScanner = new Object();

        // Given
        given(portGenerator.generatePort()).willReturn(port);
        given(tomcat.getEngine()).willReturn(engine);
        given(engine.getName()).willReturn(engineName);
        given(hashGenerator.generate(tomcat)).willReturn(hash);
        given(jarScannerFactory.create()).willReturn(jarScanner);
        given(tomcat.getHost()).willReturn(host);
        given(tomcat.addWebApp(host, contextPath, "/")).willReturn(context);
        given(tomcat.getConnector()).willReturn(connector);

        // When
        configurer.configure(tomcat, baseDir);

        // Then
        final InOrder order = inOrder(tomcat);
        order.verify(tomcat).setBaseDir(baseDir);
        order.verify(tomcat).setPort(port);
        order.verify(tomcat).getEngine();
        then(engine).should().setName(format("%s%d", engineName, hash));
        then(context).should().setJarScanner(jarScanner);
        then(connector).should().setAllowTrace(true);
    }
}