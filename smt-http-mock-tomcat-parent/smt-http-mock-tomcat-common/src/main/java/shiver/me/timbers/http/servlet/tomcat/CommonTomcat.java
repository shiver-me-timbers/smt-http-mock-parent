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

import javax.servlet.Servlet;

/**
 * @author Karl Bennett
 */
interface CommonTomcat<H, J, E extends Exception> {

    CommonEngine getEngine();

    H getHost();

    CommonContext<J> addWebApp(H host, String contextPath, String docBase);

    CommonConnector getConnector();

    void setBaseDir(String baseDir);

    void setPort(int port);

    CommonWrapper addServlet(String contextPath, String name, Servlet servlet);

    void start() throws E;

    void stop() throws E;
}
