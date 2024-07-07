package com.taotao.cloud.flink.doe.beans.windows;


import com.taotao.cloud.flink.doe.beans.OrdersBean;
import org.apache.flink.api.common.eventtime.SerializableTimestampAssigner;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.datastream.WindowedStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

import java.time.Duration;


/**
 * @Date: 2024/1/2
 * @Author: Hang.Nian.YY
 * @WX: 17710299606
 * @Tips: 学大数据 ,到多易教育
 * @DOC: https://blog.csdn.net/qq_37933018?spm=1000.2115.3001.5343
 * @Description: 全量聚合函数
 * keyBy后  时间窗口  以事件时间为时间语义
 * 1,zss,bj,100,10000
 * 2,lss,bj,100,18000
 * 3,lss,nj,100,19000
 * 4,lss,nj,100,19500
 * 5,zss,sh,100,18900
 * 6,zss,sh,100,12000
 * 7,lss,sh,100,21000
 * 8,lss,bj,100,22000
 */
public class WindowsFunctionsAll {
    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        conf.setInteger("rest.port", 8888);
        StreamExecutionEnvironment see = StreamExecutionEnvironment.createLocalEnvironmentWithWebUI(conf);
        see.setParallelism(1);
        DataStreamSource<String> ds = see.socketTextStream("doe01", 8899);
        // 处理数据 ,  将数据封装成Bean
        SingleOutputStreamOperator<OrdersBean> beans = ds.map(new MapFunction<String, OrdersBean>() {
            @Override
            public OrdersBean map(String value) throws Exception {
                OrdersBean orderBean = new OrdersBean();
                try {
                    String[] arr = value.split(",");
                    int oid = Integer.parseInt(arr[0]);
                    String name = arr[1];
                    String city = arr[2];
                    double money = Double.parseDouble(arr[3]);
                    long ts = Long.parseLong(arr[4]);
                    orderBean = new OrdersBean(oid, name, city, money, ts);

                } catch (Exception e) {

                }
                return orderBean;
            }
        });
        SingleOutputStreamOperator<OrdersBean> beansWithWm = beans.assignTimestampsAndWatermarks(WatermarkStrategy.<OrdersBean>forBoundedOutOfOrderness(Duration.ofSeconds(1)).withTimestampAssigner(new SerializableTimestampAssigner<OrdersBean>() {
            @Override
            public long extractTimestamp(OrdersBean element, long recordTimestamp) {
                return element.getTs();
            }
        }));

        // 对数据进行分组  按照城市
        KeyedStream<OrdersBean, String> keyed = beansWithWm.keyBy(new KeySelector<OrdersBean, String>() {
            @Override
            public String getKey(OrdersBean value) throws Exception {
                return value.getCity();
            }
        });

        /**
         * 滚动窗口
         *    keyby后的数据调用时间窗口
         *    当时间窗口触发后 ,当前窗口中的所有的数据按照分组key统计
         */
        WindowedStream<OrdersBean, String, TimeWindow> window = keyed.window(TumblingEventTimeWindows.of(Time.seconds(10)));

        /**
         * 聚合操作
         * 全量聚合
         * 泛型 1 输入  2 输出  3 key类型  4 窗口对象
         *
         */
        SingleOutputStreamOperator<Tuple2<String, Double>> res = window.process(new ProcessWindowFunction<OrdersBean, Tuple2<String, Double>, String, TimeWindow>() {
            /**
             *
             * @param key 窗口内部分组的key.
             * @param context 窗口上下文信息
             * @param elements 窗口中的数据
             * @param out 用于数据输出
             * @throws Exception
             */
            @Override
            public void process(String key, ProcessWindowFunction<OrdersBean, Tuple2<String, Double>, String, TimeWindow>.Context context, Iterable<OrdersBean> elements, Collector<Tuple2<String, Double>> out) throws Exception {
                double money = 0d;
                double  cnt = 0d ;

                // 循环 窗口内部, 每组key的所有数据
                for (OrdersBean element : elements) {
                    money += element.getMoney();
                    cnt ++ ;
                }
                out.collect(Tuple2.of(key ,money/cnt));
            }
        });

        res.print() ;


        see.execute();

    }
}
