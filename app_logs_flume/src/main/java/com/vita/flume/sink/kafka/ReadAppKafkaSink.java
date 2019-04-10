package com.vita.flume.sink.kafka;

import com.google.gson.Gson;
import com.vita.app.common.Constants;
import com.vita.app.common.log.*;
import org.apache.flume.*;
import org.apache.flume.conf.Configurable;
import org.apache.flume.sink.AbstractSink;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.util.StringUtils;

import java.util.Properties;


/**
 * 自定义sink，将flume 转出 kafka
 */
public class ReadAppKafkaSink extends AbstractSink implements Configurable {

    KafkaProducer<String, String> producer;
    String topic = "";


    @Override
    public Status process() throws EventDeliveryException {
        Status status = null;
        Channel channel = getChannel();
        Transaction transaction = channel.getTransaction();
        transaction.begin();

        try {
            Event event = channel.take();
            if (event == null) {
                status = Status.BACKOFF;
            }

            String body = new String(event.getBody(), "UTF-8");
            Gson gson = new Gson();
            AppBaseLog appBaseLog = gson.fromJson(body, AppBaseLog.class);
            String logType = appBaseLog.getLogType();
            System.out.println(body);

            if ((logType == null || "".equals(logType))) {
                topic = Constants.TOPIC_COLLECT;
                body = "{\"value\":\"no message\"}";
            } else {
                topic = logType;
            }

            //生产者
            ProducerRecord<String, String> record = new ProducerRecord<>(topic, new String(body));
            producer.send(record);
            transaction.commit();
            status = Status.READY;

        } catch (Exception e) {
            transaction.rollback();
            status = Status.BACKOFF;
        } finally {
            transaction.close();
        }
        return status;
    }

    @Override
    public void configure(Context context) {
        Properties prop = new Properties();

        prop.put("bootstrap.servers", Constants.BOOTSTRAP_SERVERS);
        prop.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        prop.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        producer = new KafkaProducer<String, String>(prop);
    }
}
