package com.taotao.cloud.flink.doe.sources;


import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @Date: 2023/12/27
 * @Author: Hang.Nian.YY
 * @WX: 17710299606
 * @Tips: 学大数据 ,到多易教育
 * @DOC: https://blog.csdn.net/qq_37933018?spm=1000.2115.3001.5343
 * @Description: kafkasource  , flink模拟kafka消费者消费数据
 * 分区 , 偏移量
 * kafka作为消息中间件 ,对接各种数据源 , 削峰填谷
 */
public class Demo04Kafka {
    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        conf.setInteger("rest.port", 8888);
        StreamExecutionEnvironment see = StreamExecutionEnvironment.createLocalEnvironmentWithWebUI(conf);

        /**
         * kafka地址
         * topic
         * 消费组
         * 消费起始位置
         * 数据的反序列化方式
         */
        KafkaSource<String> kafkaSource =
                KafkaSource
                        .<String>builder()  // 注意 泛型
                        // kakfa地址
                        .setBootstrapServers("doe01:9092,doe02:9092,doe03:9092")
                        // 消费主题
                        .setTopics("doe44")
                        // 消费组
                        .setGroupId("group1")
                        // 消费位置  latest最新数据  所有数据
                        .setStartingOffsets(OffsetsInitializer.latest())
                        // 数据的反序列化方式
                        .setValueOnlyDeserializer(new SimpleStringSchema())
                        .build();

        DataStreamSource<String> ds = see.fromSource(kafkaSource, WatermarkStrategy.noWatermarks(), "kafak-source");

        ds.print() ;
        see.execute() ;



    }
}
