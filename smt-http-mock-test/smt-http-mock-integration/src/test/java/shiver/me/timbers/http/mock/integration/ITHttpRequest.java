package shiver.me.timbers.http.mock.integration;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import shiver.me.timbers.http.mock.HttpMockServer;

import javax.ws.rs.core.Response;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static shiver.me.timbers.http.mock.HttpMock.givenHttp;
import static shiver.me.timbers.http.mock.HttpMock.status;
import static shiver.me.timbers.http.mock.HttpMock.thenHttp;
import static shiver.me.timbers.http.mock.integration.HttpClients.createClient;

public class ITHttpRequest {

    private static final int OK = 200;

    private HttpMockServer server;

    @Before
    public void setUp() {
        server = new HttpMockServer();
    }

    @After
    public void tearDown() {
        server.stop();
    }

    @Test
    public void Can_mock_an_http_request() {

        // Given
        givenHttp(server.get()).willRespond(status(OK));

        // When
        final Response actual = createClient(server).get();

        // Then
        thenHttp(server).should().get();
        assertThat(actual.getStatus(), is(OK));
    }
}

