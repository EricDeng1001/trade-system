package ai.techfin.tradesystem.repository;

import ai.techfin.tradesystem.TradeSystemApp;
import ai.techfin.tradesystem.domain.HibernateTestBase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.HashSet;

@ExtendWith({SpringExtension.class})
@SpringBootTest(classes = TradeSystemApp.class)
@EmbeddedKafka
@ActiveProfiles("test")
class HibernateTestRepositoryIT {

    @Autowired
    private HibernateTestRepository hibernateTestRepository;

    @BeforeEach
    void setUp() {
        hibernateTestRepository.save(new HibernateTestBase("string 1", "string 2",
                                                           new HashSet<>(Arrays.asList(1, 2, 3))));
    }

    @Test
    void testEntityLoad() {
        var set = hibernateTestRepository.findAll();
    }

    @Test
    void testEntityLoadAndGet() {
        var set = hibernateTestRepository.findAll();
        var entity = set.get(0);
    }

    @Test
    void testEntityLoadAndGetProp() {
        HibernateTestBase entity = hibernateTestRepository.findById(1L).get();
        var prop = entity.getStringValue();
        var set = entity.getIntegers();
        for (int x: set) {
            System.out.println(x);
        }
    }

    @Test
    void testEntitySet() {
        HibernateTestBase entity = hibernateTestRepository.findById(1L).get();
        entity.setStringValueWithColumn("x");
        hibernateTestRepository.save(entity);
    }

}