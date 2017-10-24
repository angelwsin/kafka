package kafka_Producer.kafka_Producer;

import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.Future;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

/**
 * 
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	 Properties props = new Properties();
    	 props.put("bootstrap.servers", "localhost:9092");
    	 props.put("acks", "all");
    	 props.put("retries", 0);
    	 props.put("batch.size", 16384);
    	 props.put("linger.ms", 1);
    	 props.put("buffer.memory", 33554432);
    	 props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
    	 props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
    	 try(Producer<String, String> producer = new KafkaProducer<>(props);) {
    		 for(int i=0;i<10;i++){
    			 //send Asynchronously send a record to a topic and invoke the provided callback when the send has been acknowledged.
    			 Future<RecordMetadata> result =producer.send(new ProducerRecord<String, String>("connect-test", Integer.toString(i), Integer.toString(i))) ;
    			 //同步等待 result.get();  确认
    			 
    			 
        	 }
		} catch (Exception e) {
			 e.printStackTrace();
		}
    	
    	while(true);
    	
    }
    
    public void  sendCall(){
    	 Properties props = new Properties();
    	 props.put("bootstrap.servers", "localhost:9092");
    	 props.put("acks", "all");
    	 props.put("retries", 0);
    	 props.put("batch.size", 16384);
    	 props.put("linger.ms", 1);
    	 props.put("buffer.memory", 33554432);
    	 props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
    	 props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
    	 try(Producer<String, String> producer = new KafkaProducer<>(props);) {
    			 //send Asynchronously send a record to a topic and invoke the provided callback when the send has been acknowledged.
    			  producer.send(new ProducerRecord<String, String>("my-topic", "send", "send"),new Callback() {
					
					@Override
					public void onCompletion(RecordMetadata metadata, Exception exception) {
						//异步  确认
						if(!Objects.isNull(exception)){
							exception.printStackTrace();
						}else{
							System.out.println(metadata.offset());
							
						}
							
						
					}
				}) ;
    			 
    			 
    			 
		} catch (Exception e) {
			 e.printStackTrace();
		}
    }
}
