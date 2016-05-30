package shiver.me.timbers.http.mock.integration;

import static shiver.me.timbers.data.random.RandomStrings.buildSomeString;

class RandomHttp {

    static String somePath() {
        return "/" + buildSomeString().thatContainsAlphanumericCharacters().withLengthBetween(1, 10).build();
    }
}
