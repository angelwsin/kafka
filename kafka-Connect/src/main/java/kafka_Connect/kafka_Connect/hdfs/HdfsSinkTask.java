package kafka_Connect.kafka_Connect.hdfs;

import java.util.Collection;
import java.util.Map;

import org.apache.kafka.connect.sink.SinkRecord;
import org.apache.kafka.connect.sink.SinkTask;

public class HdfsSinkTask extends SinkTask{

	@Override
	public String version() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void start(Map<String, String> props) {
		// TODO Auto-generated method stub
		
	}

	//真正的 put 数据到指定的系统 如 hdfs
	@Override
	public void put(Collection<SinkRecord> records) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}

}
