package com.taotao.cloud.flink.doe.waters;


import org.apache.flink.api.common.eventtime.SerializableTimestampAssigner;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.time.Duration;

/**
 * @Date: 2023/12/31
 * @Author: Hang.Nian.YY
 * @WX: 17710299606
 * @Tips: 学大数据 ,到多易教育
 * @DOC: https://blog.csdn.net/qq_37933018?spm=1000.2115.3001.5343
 * @Description: 水位线  类  Watermark(ts) ;
 * 水位线的生成逻辑
 * 使用事件时间语义处理数据时 , 在数据流上分配水位线(在数据流中生成水位线)
 */
public class WatermarkDemo {
    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        conf.setInteger("rest.port", 8888);
        StreamExecutionEnvironment see = StreamExecutionEnvironment.createLocalEnvironmentWithWebUI(conf);
        see.setParallelism(1);

        DataStreamSource<String> ds = see.socketTextStream("doe01", 8899);
        /**
         * forMonotonousTimestamps:  数据是有序的
         * 能保证数据流时按照数据的生成时间流入到算子中
         *   16s  12s  11s  10s
         *
         */
        ds.assignTimestampsAndWatermarks(WatermarkStrategy.forMonotonousTimestamps());
        SingleOutputStreamOperator<String> ds2 = ds.assignTimestampsAndWatermarks(
                // 1) 分配水位线  允许5s乱序
                WatermarkStrategy.<String>forBoundedOutOfOrderness(Duration.ofMillis(5000))
                        // 2) 告诉程序 , 数据中的时间字段
                        .withTimestampAssigner(new SerializableTimestampAssigner<String>() {
                            @Override
                            public long extractTimestamp(String element, long recordTimestamp) {
                                //oid01,zss,BJ,100,10000
                                String[] split = element.split(",");
                                return Long.parseLong(split[1]);
                            }
                        })

        );



        SingleOutputStreamOperator<String> ds3 = ds2.map(String::toUpperCase).setParallelism(3);
        ds3.print() ;

        see.execute() ;
        // 时间窗口计算
      //  ds2.windowAll(TumblingEventTimeWindows.of(Time.seconds(10))) ;

    }

}
