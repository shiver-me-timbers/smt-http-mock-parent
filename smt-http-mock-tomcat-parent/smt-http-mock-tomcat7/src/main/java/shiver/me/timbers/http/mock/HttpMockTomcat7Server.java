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

package shiver.me.timbers.http.mock;

import shiver.me.timbers.http.servlet.tomcat.Tomcat7Container;

/**
 * @author Karl Bennett
 */
public class HttpMockTomcat7Server extends HttpMockContainerServer {

    public HttpMockTomcat7Server() {
        this(0);
    }

    public HttpMockTomcat7Server(int port) {
        super(new Tomcat7Container(port, "mock"));
    }
}
