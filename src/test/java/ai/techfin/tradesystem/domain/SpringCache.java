package ai.techfin.tradesystem.domain;

import ai.techfin.tradesystem.repository.UserRepository;
import org.springframework.cache.annotation.Cacheable;

public class SpringCache {

    private int root;

    public SpringCache(int root) {
        this.root = root;
    }

    @Cacheable(UserRepository.USERS_BY_LOGIN_CACHE)
    public int getRoot() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return root * root;
    }

}