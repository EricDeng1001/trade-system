package ai.techfin.tradesystem.config;

import ai.techfin.tradesystem.aop.logging.LoggingAspect;

import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;

@Configuration
@EnableAspectJAutoProxy
public class LoggingAspectConfiguration {

    @Bean
    @Profile(ApplicationConstants.Profile.DEVELOPMENT)
    public LoggingAspect loggingAspect(Environment env) {
        return new LoggingAspect(env);
    }
}
