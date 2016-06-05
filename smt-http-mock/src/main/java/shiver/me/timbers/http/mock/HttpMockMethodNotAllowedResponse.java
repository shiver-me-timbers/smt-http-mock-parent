package shiver.me.timbers.http.mock;

import static shiver.me.timbers.http.StatusCodes.METHOD_NOT_ALLOWED;
import static shiver.me.timbers.http.mock.HttpMockResponses.buildSignature;

/**
 * @author Karl Bennett
 */
class HttpMockMethodNotAllowedResponse implements HttpMockResponse {

    private final HttpMockArguments arguments;
    private final Throwable cause; // TODO Add logging.

    HttpMockMethodNotAllowedResponse(HttpMockArguments arguments, Throwable cause) {
        this.arguments = arguments;
        this.cause = cause;
    }

    @Override
    public int getStatus() {
        return METHOD_NOT_ALLOWED;
    }

    @Override
    public String getBodyAsString() {
        return "No http mock method found with the signature: " +
            buildSignature(arguments.getHttpMethod(), arguments.toParameterTypes());
    }
}
