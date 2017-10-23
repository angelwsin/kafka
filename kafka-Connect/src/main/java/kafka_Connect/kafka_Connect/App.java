package kafka_Connect.kafka_Connect;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
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
