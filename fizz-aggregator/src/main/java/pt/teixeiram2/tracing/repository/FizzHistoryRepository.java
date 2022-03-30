package pt.teixeiram2.tracing.repository;

import org.springframework.data.repository.CrudRepository;
import pt.teixeiram2.tracing.model.FizzHistory;

public interface FizzHistoryRepository extends CrudRepository<FizzHistory, Long> {
}
