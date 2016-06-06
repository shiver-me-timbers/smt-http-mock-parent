package shiver.me.timbers.http.mock.integration;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import shiver.me.timbers.http.mock.HttpMockServer;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = HttpMockConfiguration.class)
@WebIntegrationTest({"server.port=0", "management.port=0"})
public class ITHttpRequestWithHeadersAndBody extends AbstractHttpRequestWithHeadersAndBody {

    @Autowired
    private HttpMockServer http;

    @Before
    public void setUp() {
        http.ignoreHeaders("Host", "Connection", "User-Agent", "Accept", "Content-Type", "Content-Length");
    }

    @Override
    protected HttpMockServer http() {
        return http;
    }
}
