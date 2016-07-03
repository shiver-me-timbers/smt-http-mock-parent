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

import org.junit.Test;

import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

public class NullHttpMockMethodCallTest {

    @Test
    public void Can_invoke_a_null_http_mock_method_call() {

        // When
        final HttpMockResponse actual = new NullHttpMockMethodCall().invoke(new Object());

        // Then
        assertThat(actual, nullValue());
    }
}