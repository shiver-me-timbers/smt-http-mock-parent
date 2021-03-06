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

package shiver.me.timbers.http.servlet;

import org.junit.Test;
import shiver.me.timbers.http.Service;

import javax.servlet.Servlet;

import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static shiver.me.timbers.matchers.Matchers.hasField;

public class ServiceToServletConverterTest {

    @Test
    public void Can_convert_a_service_into_a_servlet() {

        // Given
        final Service service = mock(Service.class);

        // When
        final Servlet actual = new ServiceToServletConverter().convert(service);

        // Then
        assertThat(actual, hasField("service", service));
    }
}