package pt.teixeiram2.tracing.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;
import pt.teixeiram2.tracing.service.FizzHistoryService;

@Service
public class ConsumerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerService.class);

    private final FizzHistoryService fizzHistoryService;

    public ConsumerService(FizzHistoryService fizzHistoryService) {
        this.fizzHistoryService = fizzHistoryService;
    }

    @KafkaListener(topics = "${kafka.topic}", groupId = "${kafka.groupId}", autoStartup = "true")
    public void listen(String message, Acknowledgment acknowledgment) {
        try {
            long id = fizzHistoryService.saveFizzMessage(message);
            LOGGER.info("Saved message {} with id {}", message, id);
        } catch (Exception e) {
            LOGGER.error("Failed to save message {}", message);
        }
        finally {
            acknowledgment.acknowledge();
        }
    }

}
