package com.wiley.support;

import org.hamcrest.*;

import static com.wiley.support.Log.LOGGER;

public class Assert {
    public static void assertThat(String expected, boolean assertion) {
        if (!assertion) {
            LOGGER.error(String.format("[FAILED] Assertion failed: %s", expected));
            throw new AssertionError(expected);
        }
        LOGGER.info("[PASSED] Assertion passed. " + expected);
    }

    public static <T> void assertThat(T actual, Matcher<? super T> matcher) {
        assertThat("", actual, matcher);
    }

    public static <T> void assertThat(String expected, T actual, Matcher<? super T> matcher) {
        if (!matcher.matches(actual)) {
            StringDescription description = new StringDescription();
            description.appendText(expected).appendText(". Reason:\nExpected: ").appendDescriptionOf(matcher).appendText("\n     but: ");
            matcher.describeMismatch(actual, description);
            LOGGER.error(String.format("[FAILED] Assertion failed: %s", description.toString()));
            throw new AssertionError(description.toString());
        }
        LOGGER.info("[PASSED] Assertion passed. " + expected);
    }
}
