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

import shiver.me.timbers.http.Container;

/**
 * @author Karl Bennett
 */
public class HttpMockContainerServer implements HttpMockServer {

    private final Container container;
    private final HttpMockService service;

    HttpMockContainerServer(Container container) {
        this(container, new HttpMockService());
    }

    HttpMockContainerServer(Container container, HttpMockService service) {
        this.container = container;
        this.service = service;
        container.register(service);
        container.start();
    }

    @Override
    public void ignoreHeaders(String... names) {
        service.ignoreHeaders(names);
    }

    @Override
    public int getPort() {
        return container.getPort();
    }

    @Override
    public <T> T mock(T handler) {
        return service.registerHandler(handler);
    }

    @Override
    public void stop() {
        container.stop();
    }
}
