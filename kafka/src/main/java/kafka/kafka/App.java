package kafka.kafka;

/**
 *http://kafka.apache.org/intro
 *
 *Kafka is run as a cluster on one or more servers.
 The Kafka cluster stores streams of records in categories called topics.
 Each record consists of a key, a value, and a timestamp
 */
public class App 
{
    public static void main( String[] args )
    {
    	
    }
    
    /**
     * Topics and Logs
     * 
     * 每个分区是一个有序的，不可变的记录序列，不断附加到一个结构化的提交日志中。 每个分区中的记录都被分配一个顺序的id号，称为唯一标识分区中每个记录的偏移量。
     * 
     * Kafka集群保留所有已发布的记录 - 无论它们是否已被使用 - 使用可配置的保留期限。 例如，如果保留策略设置为两天，则在发布记录后的两天内，该策略可用于消费，之后将其丢弃以释放空间。 
     * kafka的性能在数据大小方面是有效的，因此长时间存储数据并不成问题。
     * 
     * 
     * 实际上，在每个消费者基础上保留的唯一元数据是消费者在日志中的偏移或位置。 这个偏移量由消费者控制：通常消费者会在读取记录时线性地提高其偏移，
     * 但实际上，由于位置由消费者控制，它可以以任何顺序消耗记录。 例如，消费者可以重置为较旧的偏移量以重新处理来自过去的数据或跳过最近的记录，并从“现在”开始消费。
     * 这种特征的组合意味着kafka消费者非常便宜 - 他们可以来回去对集群或其他消费者没有太大的影响。 例如，您可以使用我们的命令行工具来“tail”任何主题的内容，而无需更改现有消费者所消耗的内容。
     * 
     * 日志中的分区有几个目的。 首先，它们允许日志扩展到适合单个服务器的大小。 每个单独的分区必须适合托管它的服务器，但主题可能有很多分区，因此它可以处理任意数量的数据。 第二，它们作为并行性的单位 - 更重要的是这一点。
     * 
     * 
     * Distribution
     * 日志的分区分布在Kafka集群中的服务器上，每个服务器处理数据并请求分区的一部分。 每个分区都通过可配置数量的服务器进行复制，以实现容错。
     * 每个分区有一个服务器充当“领导者”，零个或多个服务器充当“追随者”。 领导者处理对分区的所有读取和写入请求，而追随者被动地复制领导者。 如果领导失败，
     *其中一个追随者将自动成为新的领导者。 每个服务器作为其一些分区的领导者，并且其他分支的追随者，因此在集群内负载平衡良好。
     *
     *Producers
     *生产者将数据发布到他们选择的主题。 生产者负责选择要分配给主题中哪个分区的记录。 这可以通过循环方式简单地平衡负载，或者可以根据某些语义分区功能（例如基于记录中的某些关键字）来完成。 更多关于使用分区在一秒钟！
     * 
     * Consumers
     * 
     * 消费者使用消费者组名称标注自己，并将发布到主题的每条记录传递到每个订阅消费者组中的一个消费者实例。 消费者实例可以在单独的进程中或在单独的机器上。
     * 如果所有消费者实例具有相同的消费者组，则记录将在消费者实例上有效地负载平衡。
     * 如果所有消费者实例具有不同的消费者群体，则每个记录将被广播给所有消费者进程。
     * 
     * 然而，更常见的是，我们发现主题具有少量的消费者群体，每个“逻辑订户”一个。 每个组由许多消费者实例组成，用于可扩展性和容错性。 这只不过是发布订阅语义，用户是一组消费者而不是单个进程。
     * 在Kafka中实现消费的方式是通过将日志中的分区划分到消费者实例上，以便每个实例都是任何时间点的“公平共享”分区的唯一消费者。 维护成员资格的过程由kafka协议动态处理。 如果新的实例加入组，他们将从组中的其他成员接管一些分区; 如果一个实例死机，它的分区将被分配给剩余的实例。
     * Kafka只提供一个分区内的记录的总顺序，而不是主题中的不同分区之间的总顺序。 每分区排序结合按键分配数据的能力足以满足大多数应用。 但是，如果您需要总共订单超过记录，则可以使用仅具有一个分区的主题来实现，尽管这仅意味着每个消费者组只有一个消费者进程。
     * 
     * Guarantees
     * 
     * 生产者发送到特定主题分区的消息将按照发送的顺序进行附加。 也就是说，如果记录M1由与记录M2相同的制造商发送，并且首先发送M1，则M1将具有比M2更低的偏移并且在日志中较早出现。
     * 消费者实例按照存储在日志中的顺序查看记录。
     * 对于具有复制因子N的主题，我们将容忍最多N-1个服务器故障，而不会丢失提交到日志的任何记录。
     * 
     * Kafka as a Messaging System
     * 消息传统传统上有两种模式：排队和发布 - 订阅。 在队列中，消费者池可以从服务器读取，每条记录都转到其中一个; 在发布订阅中，记录广播给所有消费者。 
     * 这两个模型中的每一个都有实力和弱点。 排队的优点是它允许您在多个消费者实例上分配数据处理，从而可以扩展您的处理。 不幸的是，队列不是多用户 - 一旦一个进程读取数据，它就会消失。
     *  发布订阅允许您将数据广播到多个进程，但无法缩放处理，因为每个消息都发送给每个用户。
     *  
     * 对比 kafka 的 topic和customer group
     * 
     *  kafka消费群体概念概括了这两个概念。 与队列一样，消费者组允许您对进程集合（消费者组的成员）进行分割处理。 与发布订阅一样，Kafka允许您将消息广播到多个消费者组。
     *  kafka模型的优点在于，每个主题都具有这两个属性 - 它可以扩展处理，也是多用户 - 不需要选择一个或另一个。
     *  kafka也比传统的邮件系统有更强大的订购保证。
     *  
     *  传统队列在服务器上保持顺序的记录，如果多个消费者从队列中消费，则服务器按照存储的顺序输出记录。 然而，虽然服务器按顺序输出记录，但是记录被异步传递给消费者，所以他们可能会在不同的消费者处按顺序到达。 
     *  这有效地意味着在并行消耗的情况下，记录的排序丢失。 消息传递系统通常通过使“唯一消费者”的概念只允许一个进程从队列中消费，但这当然意味着处理中没有并行性。
     * 
     *  kafka做得更好 通过在主题中有一个并行概念（分区），Kafka能够在消费者流程池中提供排序保证和负载平衡。 这是通过将主题中的分区分配给消费者组中的消费者来实现的，以便每个分区被组中正好一个消费者消耗。 
     *  通过这样做，我们确保消费者是该分区的唯一读者，并按顺序消耗数据。 由于有很多分区，这仍然平衡了许多消费者实例的负载。 但是请注意，消费者组中的消费者实例不能超过分区。
     * 
     * Kafka as a Storage System
     * 写入Kafka的数据写入磁盘并复制以进行容错。  Kafka允许生产者等待确认，以便在完全复制之前写入不被认为是完整的，并且即使服务器写入失败，也保证持久写入。
     * Kafka的磁盘结构使用缩放功能，Kafka将执行相同的操作，无论您在服务器上是否有50 KB或50 TB的持久数据。
     * 由于严重存储并允许客户端控制其读取位置，您可以将Kafka视为专用于高性能，低延迟的提交日志存储，复制和传播的专用分布式文件系统。
     * 有关Kafka的提交日志存储和复制设计的详细信息
     * 
     * Kafka for Stream Processing
     * 仅读取，写入和存储数据流还不够，目的是实现流的实时处理。
     * 在Kafka，流处理器是从输入主题接收数据流的任何东西，对此输入执行一些处理，并生成连续的数据流以输出主题。
     * 例如，零售应用程序可能会输入销售和出货的输入流，并输出根据此数据计算的重新排序和价格调整。
     * 可以直接使用生产者和消费者API进行简单处理。 然而对于更复杂的转换，Kafka提供了一个完全集成的Streams API。 这允许构建应用程序进行非平凡处理，以计算流中的聚合或将流连接在一起。
     * 该设施有助于解决这种类型的应用程序面临的困难问题：处理无序数据，重新处理输入作为代码更改，执行有状态计算等。
     * 流API基于Kafka提供的核心原语构建：它使用生产者和消费者API进行输入，使用Kafka进行有状态存储，并在流处理器实例之间使用相同的组机制来实现容错。
     * 
     * Putting the Pieces Together
     * 
     * 消息，存储和流处理的组合可能看起来是不寻常的，但是kafka作为流媒体平台的角色至关重要。
     * 像HDFS这样的分布式文件系统允许存储用于批处理的静态文件。 有效地，这样的系统允许存储和处理来自过去的历史数据。
     * 传统的企业邮件系统允许处理将在您订阅之后到达的未来邮件。 以这种方式构建的应用程序在未来数据到达时处理。
     * Kafka结合了这两种功能，组合对于Kafka作为流应用程序和流数据管道平台的使用至关重要。
     * 通过组合存储和低延迟订阅，流式应用程序可以以相同的方式处理过去和未来的数据。 这是一个单一的应用程序可以处理历史的，存储的数据，而不是在到达最后一个记录时结束，它可以在将来的数据到达时继续处理。
     *  这是流程处理的一般概念，其中包含批处理以及消息驱动的应用程序。
     * 同样，对于流数据流水线，订阅到实时事件的组合使得可以使用Kafka进行非常低延迟的流水线; 但是可靠地存储数据的能力使得可以将其用于必须保证数据传送的关键数据，
     * 或者与仅在周期性加载数据或可能会长时间停机以进行维护的离线系统集成。 流处理设施可以在数据到达时转换数据。
     * 
     * 
     * 
     * Use cases
     * 消息 代替 Mq
     * Website Activity Tracking 站点跟踪
     * Metrics 运行监控数据
     * Log Aggregation 日志聚合(Scribe or Flume)
     * Stream Processing  流处理 (Storm)
     * Event Sourcing 
     * Commit Log (BookKeeper)
     * 
     */
}
