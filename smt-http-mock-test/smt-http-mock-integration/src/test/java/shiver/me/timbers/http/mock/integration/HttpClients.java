package shiver.me.timbers.http.mock.integration;

import org.glassfish.jersey.client.JerseyClientBuilder;
import org.glassfish.jersey.client.JerseyInvocation;
import shiver.me.timbers.http.mock.HttpMockServer;

class HttpClients {

    static JerseyInvocation.Builder createClient(HttpMockServer server) {
        return JerseyClientBuilder.createClient().target("http://localhost:" + server.getPort()).request();
    }
}
