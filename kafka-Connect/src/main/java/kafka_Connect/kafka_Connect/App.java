package kafka_Connect.kafka_Connect;

import org.apache.kafka.connect.cli.ConnectStandalone;

/**
 * Connect API允许实现连续从某些源数据系统拉入Kafka的连接器，或者从Kafka推送到某些sink数据系统。
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	
    	try {
			ConnectStandalone.main(args);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
/*    Connectors and Tasks

    To copy data between Kafka and another system, users create a Connector for the system they want to
    pull data from or push data to. Connectors come in two flavors: SourceConnectors import data 
    from another system (e.g. JDBCSourceConnector would import a relational database into Kafka) 
    and SinkConnectors export data (e.g. HDFSSinkConnector would export the contents of a Kafka topic 
    		to an HDFS file)
    . These Tasks also come in two corresponding flavors: SourceTask and SinkTask
    
    connectors 分两种
    :input SourceConnectors    SourceTask
    :output  SinkConnectors     SinkTask
    		*/
}
