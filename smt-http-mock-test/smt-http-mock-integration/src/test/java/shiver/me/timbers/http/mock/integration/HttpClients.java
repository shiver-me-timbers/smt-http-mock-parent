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
