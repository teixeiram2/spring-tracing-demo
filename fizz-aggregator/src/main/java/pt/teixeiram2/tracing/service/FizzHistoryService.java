package pt.teixeiram2.tracing.service;


import io.opentelemetry.extension.annotations.WithSpan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Service;
import pt.teixeiram2.tracing.model.FizzHistory;
import pt.teixeiram2.tracing.repository.FizzHistoryRepository;

@Service
@ManagedResource(objectName = "pt.teixeiram2.tracing:name=FizzHistoryService", description = "Fizz.")
public class FizzHistoryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FizzHistoryService.class);

    private final FizzHistoryRepository fizzHistoryRepository;

    public FizzHistoryService(FizzHistoryRepository fizzHistoryRepository) {
        this.fizzHistoryRepository = fizzHistoryRepository;
    }

    @WithSpan
    public long saveFizzMessage(String message) {
        FizzHistory fizzHistory = new FizzHistory();
        fizzHistory.setMessage(message);
        LOGGER.info("operation='saveMessage', value='{}', msg='Saving received message'", message);
        return fizzHistoryRepository.save(fizzHistory).getId();
    }

    @ManagedOperation
    public String jmxRemote(Long id) {
        return fizzHistoryRepository.findById(id)
                .map(FizzHistory::getMessage)
                .orElse("None");
    }
}
