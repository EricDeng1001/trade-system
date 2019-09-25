package ai.techfin.tradesystem.config;

/**
 * Application constants.
 */
public final class ApplicationConstants {

    // Regex for acceptable login
    public static final String LOGIN_REGEX = "^[_.@A-Za-z0-9-]*$";

    public static final String SYSTEM_ACCOUNT = "system";

    public static final String DEFAULT_LANGUAGE = "zh-cn";

    public static final String ANONYMOUS_USER = "anonymous-user";

    public static final String XTP_BROKER_SERVICE = "xtp-broker";

    public static final class Profile {

        public static final String DEVELOPMENT = "dev";

        public static final String PRODUCTION = "prod";

        public static final String CORS = "cors";

        private Profile() {
        }

    }

    private ApplicationConstants() {
    }

}
