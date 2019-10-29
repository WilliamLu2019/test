package us.paradyme.function;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.events.ScheduledEvent;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

public class Hello {

    private Properties getRequiredParameterFromEnv(){
        return new Properties();
    }

    public void myHandler(ScheduledEvent event, Context context) {
        LambdaLogger logger = context.getLogger();
        logger.log("received : " + event.toString());
        Consumer<String, String> consumer = createConsumer();
        String topic = "COLLECTION_RESPONSE_TOPIC";
        TopicPartition topicPartition = new TopicPartition(topic, 3);
        List<TopicPartition> partitionList = new ArrayList<TopicPartition>();
        partitionList.add(topicPartition);
        consumer.assign(partitionList);

//        consumer.subscribe(Collections.singletonList(topic));
        long offset = consumer.position(topicPartition);
        logger.log("current offset is : " + offset);
//        long startTime = System.currentTimeMillis();
//        long fourteenMinutesMillis = 60*1*1000;
//        boolean run=true;
//        while (run){
//        logger.log("consuming message");
        ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(10L));
//        logger.log("message consumed: " + records.count());
        for (ConsumerRecord<String, String> record : records) {
//            logger.log("consume records : " + record.value());
            logger.log("offset = " + record.offset()+ " key = "+ record.key()+ " value = "+ record.value());

        }

        consumer.commitAsync();
//            long currentTime = System.currentTimeMillis();
//            if((currentTime-startTime)>=fourteenMinutesMillis ){
//                run = false;
//            }

//        }
//        logger.log("Stopping lambda, not waiting for timeout the next lambda instance will startup shortly");
        consumer.close();


    }

    private Consumer<String, String> createConsumer() {
        final Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                "pkc-lgk0v.us-west1.gcp.confluent.cloud:9092");
//        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "100");
//        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        props.put(ConsumerConfig.GROUP_ID_CONFIG,
                "paradyme-test");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class.getName());
//        props.put(ConsumerConfig.CLIENT_ID_CONFIG, "paradyme-lambda-consumer-1");
        props.put("ssl.endpoint.identification.algorithm", "https");
        props.put("sasl.mechanism", "PLAIN");
        props.put("request.timeout.ms", "20000");
        props.put("retry.backoff.ms", "500");
        props.put("sasl.jaas.config", "org.apache.kafka.common.security.plain.PlainLoginModule required username=\"RCATCXERWQJ4X54S\" password=\"yV8rg5qOfzwKgcpPVsbBqCD5tmEbJuOlLwpwrDqDAkDZt8ZZGp7S9nWZzvHFh2Dv\";");
        props.put("security.protocol", "SASL_SSL");
        props.put("auto.offset.reset", "earliest");
        final Consumer<String, String> consumer = new KafkaConsumer<String, String>(props);
        return consumer;
    }


}
