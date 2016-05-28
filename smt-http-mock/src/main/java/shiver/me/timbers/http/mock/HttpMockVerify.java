package shiver.me.timbers.http.mock;

import static java.lang.String.format;

/**
 * @author Karl Bennett
 */
public class HttpMockVerify {

    private final HttpMockService service;
    private final int times;

    HttpMockVerify(HttpMockService service) {
        this(service, 1);
    }

    HttpMockVerify(HttpMockService service, int times) {
        this.service = service;
        this.times = times;
    }

    public void get() {
        final int numberOfGetRequests = service.getNumberOfGetRequests();
        if (times == numberOfGetRequests) {
            return;
        }

        throw new HttpMockVerificationException(
            format(
                "The required number of GET request was not received. Actual: %d, Expected: %d",
                numberOfGetRequests,
                times
            )
        );
    }
}
