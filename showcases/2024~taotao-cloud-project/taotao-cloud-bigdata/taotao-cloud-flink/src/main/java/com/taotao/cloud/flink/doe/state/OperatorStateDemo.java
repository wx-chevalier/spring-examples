package com.taotao.cloud.flink.doe.state;


import org.apache.flink.api.common.restartstrategy.RestartStrategies;
import org.apache.flink.api.common.time.Time;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @Date: 2024/1/5
 * @Author: Hang.Nian.YY
 * @WX: 17710299606
 * @Tips: 学大数据 ,到多易教育
 * @DOC: https://blog.csdn.net/qq_37933018?spm=1000.2115.3001.5343
 * @Description:
 */
public class OperatorStateDemo {
    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        conf.setInteger("rest.port", 8888);
        StreamExecutionEnvironment see = StreamExecutionEnvironment.createLocalEnvironmentWithWebUI(conf);
        see.setParallelism(2);
        // 程序出现故障后  默认停止job  设置重启机制
        // 出现故障后选择重启   10s内最大允许重启3次
        see.setRestartStrategy(RestartStrategies.fixedDelayRestart(3, Time.seconds(10)));
       // 开启checkpoint机制  定期持久化状态数据(保证数据安全)
        see.enableCheckpointing(5000) ;
        DataStreamSource<String> ds1 = see.socketTextStream("doe01", 8899);
         // 2
        SingleOutputStreamOperator<String> ds2 = ds1.map(String::toLowerCase);
        // 使用算子状态 记录每个SubTask中处理数据的条数
        ds2.map(new MyRichFunction()) .print();

        see.execute() ;

    }
}
