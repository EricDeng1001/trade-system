package ai.techfin.tradesystem.web.rest.errors;

import java.net.URI;

final class ErrorConstants {

    private static final String PROBLEM_BASE_URL = "/";

    static final String ERR_CONCURRENCY_FAILURE = "error.concurrencyFailure";

    static final String ERR_VALIDATION = "error.validation";

    static final URI DEFAULT_TYPE = URI.create(PROBLEM_BASE_URL + "/problem-with-message");

    static final URI CONSTRAINT_VIOLATION_TYPE = URI.create(PROBLEM_BASE_URL + "/constraint-violation");

    static final URI ENTITY_NOT_FOUND_TYPE = URI.create(PROBLEM_BASE_URL + "/entity-not-found");

    static final URI INVALID_PASSWORD_TYPE = URI.create(PROBLEM_BASE_URL + "/invalid-password");

    static final URI EMAIL_ALREADY_USED_TYPE = URI.create(PROBLEM_BASE_URL + "/email-already-used");

    static final URI LOGIN_ALREADY_USED_TYPE = URI.create(PROBLEM_BASE_URL + "/login-already-used");

    static final URI EMAIL_NOT_FOUND_TYPE = URI.create(PROBLEM_BASE_URL + "/email-not-found");

    static final URI RESOURCE_NOT_EXIST_TYPE = URI.create(PROBLEM_BASE_URL + "/resource-not-exist");

    private ErrorConstants() {
    }

}
