package shiver.me.timbers.http.mock;

import static shiver.me.timbers.http.StatusCodes.NOT_FOUND;
import static shiver.me.timbers.http.mock.HttpMockResponses.buildSignature;

/**
 * @author Karl Bennett
 */
class HttpMockNotFoundResponse implements HttpMockResponse {

    private final HttpMockArguments arguments;

    HttpMockNotFoundResponse(HttpMockArguments arguments) {
        this.arguments = arguments;
    }

    @Override
    public int getStatus() {
        return NOT_FOUND;
    }

    @Override
    public String getBodyAsString() {
        return "No result returned from mock method with the signature: " +
            buildSignature(arguments.getHttpMethod(), arguments.toParameterTypes());
    }
}
