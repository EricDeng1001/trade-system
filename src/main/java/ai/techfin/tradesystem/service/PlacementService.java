package ai.techfin.tradesystem.service;

import ai.techfin.tradesystem.config.KafkaTopicConfiguration;
import ai.techfin.tradesystem.domain.Placement;
import ai.techfin.tradesystem.domain.PlacementList;
import ai.techfin.tradesystem.repository.PlacementListRepository;
import ai.techfin.xtpms.service.broker.dto.TradeResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class PlacementService {

    private static final Logger log = LoggerFactory.getLogger(PlacementService.class);

    private final PlacementListRepository placementListRepository;

    @Autowired
    public PlacementService(PlacementListRepository placementListRepository) {
        this.placementListRepository = placementListRepository;
    }

    @KafkaListener(topics = KafkaTopicConfiguration.XTP_TRADE_SUCCEED)
    private void recordResultAcc(TradeResponseDTO dto) {
        PlacementList placementList = placementListRepository.findById(dto.getPlacementId()).orElse(null);
        if (placementList == null) {
            return;
        }
        Optional<Placement> placementOptional = placementList.getPlacements().stream()
            .filter(p -> p.getStock().equals(dto.getStock())).findFirst();
        if (placementOptional.isEmpty()) {
            log.warn("an unexpected TradeResponseDTO is received: {}", dto);
            return;
        }
        var placement = placementOptional.get();
        placement.setQuantityDealt(placement.getQuantityDealt() + dto.getQuantity());
        placement.setMoneyDealt(
            dto.getPrice().multiply(BigDecimal.valueOf(dto.getQuantity())).add(placement.getMoneyDealt()));
    }

}
