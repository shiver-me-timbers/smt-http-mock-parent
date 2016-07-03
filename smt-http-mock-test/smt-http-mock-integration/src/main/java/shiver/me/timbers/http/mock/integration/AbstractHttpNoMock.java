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

import org.junit.Test;
import shiver.me.timbers.http.mock.HttpMockServer;

import javax.ws.rs.core.Response;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static shiver.me.timbers.http.StatusCodes.NOT_FOUND;
import static shiver.me.timbers.http.mock.integration.HttpClients.createClient;

public abstract class AbstractHttpNoMock {

    protected abstract HttpMockServer http();

    @Test
    public void Can_mock_nothing() {

        // When
        final Response notFound = createClient(http()).request().get();

        // Then
        assertThat(notFound.getStatus(), is(NOT_FOUND));
    }
}

