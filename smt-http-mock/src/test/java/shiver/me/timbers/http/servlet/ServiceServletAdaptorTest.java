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
import shiver.me.timbers.http.Request;
import shiver.me.timbers.http.Response;
import shiver.me.timbers.http.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.mock;
import static shiver.me.timbers.matchers.Matchers.hasField;

public class ServiceServletAdaptorTest {

    @Test
    public void Can_call_a_servlet_service() throws ServletException, IOException {

        final Service service = mock(Service.class);
        final ServletResponseWriter responseWriter = mock(ServletResponseWriter.class);
        final HttpServletRequest servletRequest = mock(HttpServletRequest.class);
        final HttpServletResponse servletResponse = mock(HttpServletResponse.class);

        final Response response = mock(Response.class);

        // Given
        given(service.call(
            (Request) argThat(allOf(instanceOf(Request.class), hasField("servletRequest", servletRequest))))
        ).willReturn(response);

        // When
        new ServiceServletAdaptor(service, responseWriter).service(servletRequest, servletResponse);

        // Then
        then(responseWriter).should().write(servletResponse, response);
    }
}