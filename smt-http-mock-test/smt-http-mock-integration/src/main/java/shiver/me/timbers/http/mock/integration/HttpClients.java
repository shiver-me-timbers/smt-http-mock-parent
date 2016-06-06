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

package shiver.me.timbers.http.mock.integration;

import org.glassfish.jersey.client.JerseyClientBuilder;
import org.glassfish.jersey.client.JerseyWebTarget;
import shiver.me.timbers.http.mock.HttpMockServer;

import static java.lang.String.format;
import static org.glassfish.jersey.client.ClientProperties.SUPPRESS_HTTP_COMPLIANCE_VALIDATION;
import static org.glassfish.jersey.client.HttpUrlConnectorProvider.SET_METHOD_WORKAROUND;

class HttpClients {

    static JerseyWebTarget createClient(HttpMockServer http) {
        return JerseyClientBuilder.createClient()
            .property(SET_METHOD_WORKAROUND, true)
            .property(SUPPRESS_HTTP_COMPLIANCE_VALIDATION, true)
            .target(format("http://localhost:%d/mock", http.getPort()));
    }
}
