package kafka_Streams.kafka_Streams;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KStreamBuilder;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	//一个Kafka客户端，允许对来自一个或多个输入主题的输入执行连续计算，并将输出发送到零个，一个或多个输出主题。
    	//可以通过使用TopologyBuilder来定义处理器的DAG拓扑结构或使用提供高级DSL定义转换的KStreamBuilder来指定计算逻辑。
    	Map<String, Object> props = new HashMap<>();
    	 props.put(StreamsConfig.APPLICATION_ID_CONFIG, "my-stream-processing-application");
    	 props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
    	 props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
    	 props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
    	 StreamsConfig config = new StreamsConfig(props);

    	 KStreamBuilder builder = new KStreamBuilder();
    	 KStream<String, String> stream = builder.stream("my-input-topic");
    	 stream.mapValues(value->value.toString()).to("my-output-topic");
    	 KafkaStreams streams = new KafkaStreams(builder, config);
    	 streams.start();
    }
}
