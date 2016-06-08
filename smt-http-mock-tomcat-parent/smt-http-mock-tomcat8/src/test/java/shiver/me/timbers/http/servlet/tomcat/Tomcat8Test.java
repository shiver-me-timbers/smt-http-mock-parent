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
import org.apache.catalina.Host;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.LifecycleState;
import org.apache.catalina.Server;
import org.apache.catalina.Wrapper;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.JarScanner;
import org.junit.Before;
import org.junit.Test;
import shiver.me.timbers.data.random.RandomIntegers;

import javax.servlet.Servlet;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static shiver.me.timbers.data.random.RandomEnums.someEnum;
import static shiver.me.timbers.data.random.RandomIntegers.*;
import static shiver.me.timbers.data.random.RandomStrings.someString;
import static shiver.me.timbers.matchers.Matchers.hasField;

public class Tomcat8Test {

    private Tomcat tomcat;
    private Tomcat8 tomcat8;

    @Before
    public void setUp() {
        tomcat = mock(Tomcat.class);
        tomcat8 = new Tomcat8(tomcat);
    }

    @Test
    public void Can_get_the_engine() {

        final Engine engine = mock(Engine.class);

        // Given
        given(tomcat.getEngine()).willReturn(engine);

        // When
        final CommonEngine actual = tomcat8.getEngine();

        // Then
        assertThat(actual, hasField("engine", engine));
    }

    @Test
    public void Can_get_the_host() {

        final Host expected = mock(Host.class);

        // Given
        given(tomcat.getHost()).willReturn(expected);

        // When
        final Host actual = tomcat8.getHost();

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void Can_add_a_web_app() {

        final Host host = mock(Host.class);
        final String contextPath = someString();
        final String docBase = someString();
        final Context context = mock(Context.class);

        // Given
        given(tomcat.getHost()).willReturn(host);
        given(tomcat.addWebapp(host, contextPath, docBase)).willReturn(context);

        // When
        final CommonContext<JarScanner> actual = tomcat8.addWebApp(host, contextPath, docBase);

        // Then
        assertThat(actual, hasField("context", context));
    }

    @Test
    public void Can_get_the_connector() {

        final Connector connector = mock(Connector.class);

        // Given
        given(tomcat.getConnector()).willReturn(connector);

        // When
        final CommonConnector actual = tomcat8.getConnector();

        // Then
        assertThat(actual, hasField("connector", connector));
    }

    @Test
    public void Can_set_the_base_dir() {

        // Given
        final String baseDir = someString();

        // When
        tomcat8.setBaseDir(baseDir);

        // Then
        verify(tomcat).setBaseDir(baseDir);
    }

    @Test
    public void Can_set_the_port() {

        // Given
        final int port = someInteger();

        // When
        tomcat8.setPort(port);

        // Then
        verify(tomcat).setPort(port);
    }

    @Test
    public void Can_add_a_servlet() {

        final String contextPath = someString();
        final String name = someString();
        final Servlet servlet = mock(Servlet.class);

        final Wrapper wrapper = mock(Wrapper.class);

        // Given
        given(tomcat.addServlet(contextPath, name, servlet)).willReturn(wrapper);

        // When
        final CommonWrapper actual = tomcat8.addServlet(contextPath, name, servlet);

        // Then
        assertThat(actual, hasField("wrapper", wrapper));
    }

    @Test
    public void Can_start() throws LifecycleException {

        // When
        tomcat8.start();

        // Then
        verify(tomcat).start();
    }

    @Test
    public void Can_stop() throws LifecycleException {

        // When
        tomcat8.stop();

        // Then
        verify(tomcat).stop();
    }
}