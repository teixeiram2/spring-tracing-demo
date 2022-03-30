package pt.teixeiram2.tracing.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pt.teixeiram2.tracing.service.FizzBuzzService;

@RestController
public class FizzBuzzController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FizzBuzzController.class);

    private final FizzBuzzService fizzBuzzService;

    private final Tracer tracer;

    public FizzBuzzController(FizzBuzzService fizzBuzzService, Tracer tracer) {
        this.fizzBuzzService = fizzBuzzService;
        this.tracer = tracer;
    }

    @GetMapping("/fizzbuzz/{value}")
    public String fizzBuzz(@PathVariable long value) {
        LOGGER.info("Trace: {}", tracer.currentSpan().context().traceId());
        return fizzBuzzService.fizzBuzz(value);
    }

    @GetMapping("/fizzbuzz/getById/{id}")
    public String getStoredFizzBuzzed(@PathVariable long id) {
        return fizzBuzzService.getStoredMessage(id);
    }
}
