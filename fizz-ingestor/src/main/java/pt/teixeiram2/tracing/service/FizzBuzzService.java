package pt.teixeiram2.tracing.service;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pt.teixeiram2.tracing.config.KafkaProperties;

import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

@Service
public class FizzBuzzService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FizzBuzzService.class);
    private static final String NONE = "None";

    private static final String jmxUrl = "service:jmx:rmi:///jndi/rmi://" + "127.0.0.1:9013" + "/jmxrmi";

    private final Producer<Long, String> kafkaProducer;
    private final KafkaProperties kafkaProperties;

    public FizzBuzzService(Producer<Long, String> kafkaProducer, KafkaProperties kafkaProperties) {
        this.kafkaProducer = kafkaProducer;
        this.kafkaProperties = kafkaProperties;
    }

    public String fizzBuzz(long value) {
        StringBuilder stringBuilder = new StringBuilder();

        if (value % 3 == 0) {
            stringBuilder.append("Fizz");
        }
        if (value % 6 == 0) {
            stringBuilder.append("Buzz");
        }
        String result = stringBuilder.length() > 0 ? stringBuilder.toString() : NONE;

        ProducerRecord<Long, String> record = new ProducerRecord<>(kafkaProperties.getTopic(), result);
        kafkaProducer.send(record);

        LOGGER.info("operation='fizzBuzz', value={}, result={}, msg='FizzBuzz'", value, result);

        return result;
    }

    public String getStoredMessage(long id) {
        LOGGER.info("Making a request for stored id {}", id);
        try (JMXConnector jmxConnector = JMXConnectorFactory.connect(new JMXServiceURL(jmxUrl))) {
            MBeanServerConnection mBeanServerConnection = jmxConnector.getMBeanServerConnection();
            ObjectName obName = new ObjectName("pt.teixeiram2.tracing:name=FizzHistoryService");
            Object result = mBeanServerConnection.invoke(obName, "jmxRemote", new Object[]{id}, new String[]{Long.class.getName()});
            return (String) result;
        } catch (Exception e) {
            LOGGER.error("Failed to invoke JMX", e);
        }

        return null;
    }
}
