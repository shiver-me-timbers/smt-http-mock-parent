package shiver.me.timbers.http.mock.integration;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import shiver.me.timbers.http.mock.HttpMockLocalServer;
import shiver.me.timbers.http.mock.HttpMockServer;

public class ITHttpRequestWithBody extends AbstractHttpRequestWithBody {

    private static HttpMockServer http;

    @BeforeClass
    public static void setUp() {
        http = new HttpMockLocalServer();
        http.ignoreHeaders("Host", "Connection", "User-Agent", "Accept", "Content-Type", "Content-Length");
    }

    @AfterClass
    public static void tearDown() {
        http.stop();
    }

    @Override
    protected HttpMockServer http() {
        return http;
    }
}
