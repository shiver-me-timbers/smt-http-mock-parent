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

import org.junit.AfterClass;
import org.junit.BeforeClass;
import shiver.me.timbers.http.mock.HttpMockServer;
import shiver.me.timbers.http.mock.HttpMockTomcat7Server;

public class ITHttpMultipleRequests extends AbstractHttpMultipleRequests {

    private static HttpMockServer http;

    @BeforeClass
    public static void setUp() {
        http = new HttpMockTomcat7Server();
    }

    @AfterClass
    public static void tearDown() {
        http.stop();
    }

    @Override
    protected HttpMockServer http() {
        return http;
    }
}
