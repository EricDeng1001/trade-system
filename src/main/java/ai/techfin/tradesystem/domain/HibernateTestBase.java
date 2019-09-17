package ai.techfin.tradesystem.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Entity
public class HibernateTestBase {

    private static final Logger log = LoggerFactory.getLogger(HibernateTestBase.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String stringValue;

    @Column
    private String stringValueWithColumn;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Integer> integers = new HashSet<>(Arrays.asList(1, 2, 3));

    @OneToOne
    private HibernateTestOneToOne hibernateTestOneToOne;

    public HibernateTestOneToOne getHibernateTestOneToOne() {
        return hibernateTestOneToOne;
    }

    public void setHibernateTestOneToOne(HibernateTestOneToOne hibernateTestOneToOne) {
        this.hibernateTestOneToOne = hibernateTestOneToOne;
    }

    public HibernateTestBase(String stringValue, String stringValueWithColumn, Set<Integer> integers) {
        this.stringValue = stringValue;
        this.stringValueWithColumn = stringValueWithColumn;
        this.integers = integers;
    }

    public HibernateTestBase() {
        log.info("no arg c called");
    }

    public Long getId() { return id; }

    public void setId(Long id) {
        log.info("setId: {}", id);
        this.id = id;
    }

    public Set<Integer> getIntegers() { return integers; }

    public void setIntegers(Set<Integer> integers) {
        log.info("setIntegers: {}", integers);
        this.integers = integers;
    }

    public String getStringValue() { return stringValue; }

    public void setStringValue(String stringValue) {
        log.info("setStringValue: {}", stringValue);
        this.stringValue = stringValue;
    }

    public String getStringValueWithColumn() { return stringValueWithColumn; }

    public void setStringValueWithColumn(String stringValueWithColumn) {
        log.info("setStringValueWithColumn: {}", stringValueWithColumn);
        this.stringValueWithColumn = stringValueWithColumn;
    }

}
