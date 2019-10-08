package ai.techfin.tradesystem.domain;

import ai.techfin.tradesystem.TradeSystemApp;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TradeSystemApp.class)
class SpringCacheTest {
    @Test
    void test() {
        SpringCache springCache2 = new SpringCache(2);
        SpringCache springCache4 = new SpringCache(4);
        log.info("{}", springCache2.getRoot());
        log.info("{}", springCache2.getRoot());
        log.info("{}", springCache4.getRoot());
    }
}