package shiver.me.timbers.http.mock.integration;

import org.glassfish.jersey.client.JerseyClientBuilder;
import org.glassfish.jersey.client.JerseyInvocation;
import shiver.me.timbers.http.mock.HttpMockServer;

import static java.lang.String.format;

class HttpClients {

    static JerseyInvocation.Builder createClient(HttpMockServer http) {
        return JerseyClientBuilder.createClient().target(format("http://localhost:%d/mock", http.getPort())).request();
    }
}
