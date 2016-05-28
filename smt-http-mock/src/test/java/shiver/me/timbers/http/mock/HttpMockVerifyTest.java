package shiver.me.timbers.http.mock;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static java.lang.String.format;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static shiver.me.timbers.data.random.RandomIntegers.someInteger;

public class HttpMockVerifyTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private HttpMockService service;
    private Integer times;
    private HttpMockVerify verify;

    @Before
    public void setUp() {
        service = mock(HttpMockService.class);
        times = someInteger();
        verify = new HttpMockVerify(service, times);
    }

    @Test
    public void Can_verify_the_correct_number_of_get_requests() {

        // Given
        given(service.getNumberOfGetRequests()).willReturn(times);

        // When
        verify.get();

        // Then
        then(service).should().getNumberOfGetRequests();
    }

    @Test
    public void Can_verify_the_incorrect_number_of_get_requests() {

        final Integer invalidTimes = someInteger();

        // Given
        given(service.getNumberOfGetRequests()).willReturn(invalidTimes);
        expectedException.expect(HttpMockVerificationException.class);
        expectedException.expectMessage(format(
            "The required number of GET request was not received. Actual: %d, Expected: %d",
            invalidTimes,
            times
        ));

        // When
        verify.get();
    }
}