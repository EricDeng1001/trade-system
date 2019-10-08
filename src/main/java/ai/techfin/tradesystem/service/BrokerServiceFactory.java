package ai.techfin.tradesystem.service;

import ai.techfin.tradesystem.config.ApplicationConstants;
import ai.techfin.tradesystem.domain.enums.BrokerType;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class BrokerServiceFactory {

    private final BrokerService xtp;

    private final BrokerService internal;

    public BrokerServiceFactory(
        @Qualifier(ApplicationConstants.BrokerService.XTP) BrokerService xtp,
        @Qualifier(ApplicationConstants.BrokerService.INTERNAL) BrokerService internal
    ) {
        this.xtp = xtp;
        this.internal = internal;
    }

    public BrokerService getBrokerService(BrokerType provider) {
        BrokerService result = null;
        switch (provider) {
            case XTP:
            case CTP:
                result = xtp;
                break;
            case INTERNAL_SIM:
                result = internal;
        }
        return result;
    }

}
