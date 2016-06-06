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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import shiver.me.timbers.http.servlet.ServiceServletAdaptor;

/**
 * @author Karl Bennett
 */
@Configuration
public class HttpMockServerConfiguration {

    @Bean
    public HttpMockService httpMockService() {
        return new HttpMockService();
    }

    @Bean
    @Autowired
    public HttpMockServer httpMockServer(final Environment environment, final HttpMockService httpMockService) {
        return new HttpMockServer() {
            @Override
            public void ignoreHeaders(String... names) {
                httpMockService.ignoreHeaders(names);
            }

            @Override
            public int getPort() {
                return Integer.valueOf(environment.getProperty("local.server.port"));
            }

            @Override
            public <T> T mock(T handler) {
                return httpMockService.registerHandler(handler);
            }

            @Override
            public void stop() {
                throw new UnsupportedOperationException();
            }
        };
    }

    @Bean
    @Autowired
    public ServletRegistrationBean httpMockServlet(HttpMockService httpMockService) {
        final ServletRegistrationBean registrationBean = new ServletRegistrationBean();
        registrationBean.setServlet(new ServiceServletAdaptor(httpMockService));
        registrationBean.addUrlMappings("/mock/*");
        return registrationBean;
    }
}
