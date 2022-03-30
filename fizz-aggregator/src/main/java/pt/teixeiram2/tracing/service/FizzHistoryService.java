package pt.teixeiram2.tracing.service;


import org.springframework.cloud.sleuth.annotation.NewSpan;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Service;
import pt.teixeiram2.tracing.model.FizzHistory;
import pt.teixeiram2.tracing.repository.FizzHistoryRepository;

@Service
@ManagedResource(objectName = "pt.teixeiram2.tracing:name=FizzHistoryService", description = "Fizz.")
public class FizzHistoryService {

    private final FizzHistoryRepository fizzHistoryRepository;

    public FizzHistoryService(FizzHistoryRepository fizzHistoryRepository) {
        this.fizzHistoryRepository = fizzHistoryRepository;
    }

    @NewSpan("saveFizzMessage")
    public long saveFizzMessage(String message) {
        FizzHistory fizzHistory = new FizzHistory();
        fizzHistory.setMessage(message);
        return fizzHistoryRepository.save(fizzHistory).getId();
    }

    @ManagedOperation
    public String jmxRemote(Long id) {
        return fizzHistoryRepository.findById(id)
                .map(FizzHistory::getMessage)
                .orElse("None");
    }
}
