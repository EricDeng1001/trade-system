package ai.techfin.tradesystem.config;

/**
 * Application constants.
 */
public final class ApplicationConstants {

    // Regex for acceptable login
    public static final String LOGIN_REGEX = "^[_.@A-Za-z0-9-]*$";

    // user module const
    public static final String SYSTEM_ACCOUNT = "system";

    public static final String ANONYMOUS_USER = "anonymous-user";

    public static final String DEFAULT_LANGUAGE = "zh-cn";

    private ApplicationConstants() {}

    // others
    public static final class BrokerService {

        public static final String XTP = "xtp-broker";

        public static final String INTERNAL = "int-broker";

    }

    public static final class Profile {

        public static final String DEVELOPMENT = "dev";

        public static final String PRODUCTION = "prod";

        public static final String CORS = "cors";

        public static final String TEST = "test";
        private Profile() {}

    }

}
