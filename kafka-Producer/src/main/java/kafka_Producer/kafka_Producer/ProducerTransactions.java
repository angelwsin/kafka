package kafka_Producer.kafka_Producer;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.KafkaException;
import org.apache.kafka.common.errors.AuthorizationException;
import org.apache.kafka.common.errors.OutOfOrderSequenceException;
import org.apache.kafka.common.errors.ProducerFencedException;
import org.apache.kafka.common.serialization.StringSerializer;

public class ProducerTransactions {
	
	
	public static void main(String[] args) {
		 Properties props = new Properties();
		 props.put("bootstrap.servers", "localhost:9092");
		 props.put("transactional.id", "my-transactional-id");
		 
		 try(Producer<String, String> producer = new KafkaProducer<>(props, new StringSerializer(), new StringSerializer());) {
			 producer.initTransactions();
		     producer.beginTransaction();
		     for (int i = 0; i < 100; i++)
		         producer.send(new ProducerRecord<>("my-topic", Integer.toString(i), Integer.toString(i)));
		     producer.commitTransaction();
		 } catch (ProducerFencedException | OutOfOrderSequenceException | AuthorizationException e) {
			 e.printStackTrace();
		 } catch (KafkaException e) {
			 e.printStackTrace();
		 }
		
	}

}
