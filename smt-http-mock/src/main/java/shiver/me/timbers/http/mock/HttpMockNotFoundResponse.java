package shiver.me.timbers.http.mock;

import static java.lang.String.format;
import static shiver.me.timbers.http.StatusCodes.NOT_FOUND;
import static shiver.me.timbers.http.mock.HttpMockResponses.buildRequest;
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
        return format(
            "No result returned from mock method with the signature \"%s\" for request \"%s\".",
            buildSignature(arguments.getHttpMethod(), arguments.toParameterTypes()),
            buildRequest(arguments.getHttpMethod(), arguments.toParameters())
        );
    }
}
