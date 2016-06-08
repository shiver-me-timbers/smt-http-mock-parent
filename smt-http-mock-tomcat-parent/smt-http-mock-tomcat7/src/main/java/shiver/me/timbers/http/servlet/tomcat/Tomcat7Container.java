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
import shiver.me.timbers.http.servlet.ServiceToServletConverter;

/**
 * @author Karl Bennett
 */
public class Tomcat7Container extends TomcatContainer<Host, JarScanner, LifecycleException> {

    public Tomcat7Container(int port, String contextPath) {
        this(port, contextPath, TEMP_DIR);
    }

    private Tomcat7Container(int port, String contextPath, String tempDir) {
        super(new Tomcat7Configurer(port, contextPath), new Tomcat7(new Tomcat()), new ServiceToServletConverter(), tempDir);
    }
}
