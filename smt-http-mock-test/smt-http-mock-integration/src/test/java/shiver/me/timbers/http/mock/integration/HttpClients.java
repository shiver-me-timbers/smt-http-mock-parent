package shiver.me.timbers.http.mock.integration;

import org.glassfish.jersey.client.JerseyClientBuilder;
import org.glassfish.jersey.client.JerseyInvocation;
import shiver.me.timbers.http.mock.HttpMockServer;

import static java.lang.String.format;
import static org.glassfish.jersey.client.HttpUrlConnectorProvider.SET_METHOD_WORKAROUND;

class HttpClients {

    static JerseyInvocation.Builder createClient(HttpMockServer http) {
        return JerseyClientBuilder.createClient()
            .property(SET_METHOD_WORKAROUND, true)
            .target(format("http://localhost:%d/mock", http.getPort())).request();
    }
}
