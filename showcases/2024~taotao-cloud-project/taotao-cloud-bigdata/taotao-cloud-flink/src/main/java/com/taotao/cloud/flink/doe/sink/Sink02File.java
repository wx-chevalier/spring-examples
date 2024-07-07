package com.taotao.cloud.flink.doe.sink;


import org.apache.flink.api.common.serialization.SimpleStringEncoder;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.connector.file.sink.FileSink;
import org.apache.flink.core.fs.Path;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.sink.filesystem.OutputFileConfig;
import org.apache.flink.streaming.api.functions.sink.filesystem.bucketassigners.DateTimeBucketAssigner;
import org.apache.flink.streaming.api.functions.sink.filesystem.rollingpolicies.DefaultRollingPolicy;

import java.time.Duration;

/**
 * @Date: 2023/12/30
 * @Author: Hang.Nian.YY
 * @WX: 17710299606
 * @Tips: 学大数据 ,到多易教育
 * @DOC: https://blog.csdn.net/qq_37933018?spm=1000.2115.3001.5343
 * @Description:
 */
public class Sink02File {
    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        conf.setInteger("rest.port", 8888);
        StreamExecutionEnvironment see = StreamExecutionEnvironment.createLocalEnvironmentWithWebUI(conf);
        see.enableCheckpointing(1000) ;

        see.setParallelism(2) ;
       // 数据源  处理数据  结果
        SingleOutputStreamOperator<String> ds = see.socketTextStream("doe01", 8899).map(String::toUpperCase);

        OutputFileConfig outputFileConfig = new OutputFileConfig("doe44-" , ".csv");

        // 将结果输出到文件中
        FileSink fileSink = FileSink
                .<String>forRowFormat(new Path("data/res"), new SimpleStringEncoder())
                // 设置输出文件滚动策略  定期   大小   无数据时间间隔
                .withRollingPolicy(
                        DefaultRollingPolicy
                                .builder()
                        .withRolloverInterval(Duration.ofMinutes(1))
                                .build())
                // 检查生成桶文件夹的时间间隔
                .withBucketCheckInterval(30000)
                // 输出文件配置对象
                .withOutputFileConfig(outputFileConfig)
                //存储文件的桶文件夹的命名格式  时间文件夹
                .withBucketAssigner(new DateTimeBucketAssigner())
                .build();

        ds.sinkTo(fileSink) ;

        see.execute() ;


    }
}
