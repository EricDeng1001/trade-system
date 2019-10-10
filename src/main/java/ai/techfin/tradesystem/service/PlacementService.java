package ai.techfin.tradesystem.service;

import ai.techfin.tradesystem.config.KafkaTopicConfiguration;
import ai.techfin.tradesystem.domain.Placement;
import ai.techfin.tradesystem.domain.PlacementList;
import ai.techfin.tradesystem.repository.PlacementListRepository;
import ai.techfin.xtpms.service.broker.dto.TradeResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlacementService {
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
        List<Placement> placements = placementList.getPlacements().stream()
            .filter(p -> p.getStock().equals(dto.getStock())).collect(Collectors.toList());
        Placement placement = placements.get(0);
        placement.setQuantityDealt(placement.getQuantityDealt() + dto.getQuantity());
        placement.setMoneyDealt(dto.getPrice().multiply(BigDecimal.valueOf(dto.getQuantity())).add(placement.getMoneyDealt()));
    }
}
