package com.taotao.cloud.flink.doe.waters;


import com.taotao.cloud.flink.doe.beans.OrdersBean;
import org.apache.flink.api.common.eventtime.SerializableTimestampAssigner;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.AllWindowedStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;

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
 * 1,zss,bj,100,10000
 * 2,lss,bj,100,12000
 * 3,ww,bj,100,13000
 * 3,ww,bj,100,13000
 * 4,zl,bj,100,21000
 * 5,tg,bj,100,23000
 * 6,tg,bj,100,32000  -  29999 触发  [20S~30S)
 */
public class WatermarkDemoActiveWindow {
    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        conf.setInteger("rest.port", 8888);
        StreamExecutionEnvironment see = StreamExecutionEnvironment.createLocalEnvironmentWithWebUI(conf);
        see.setParallelism(1) ;

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

        // 分配水位线
        //  beans.assignTimestampsAndWatermarks(WatermarkStrategy.noWatermarks());
        // 不允许延迟  [单调递增的数据时使用]
         beans.assignTimestampsAndWatermarks(WatermarkStrategy.<OrdersBean>forMonotonousTimestamps()) ;
        SingleOutputStreamOperator<OrdersBean> beansWithWM = beans.assignTimestampsAndWatermarks(
                WatermarkStrategy.<OrdersBean>forBoundedOutOfOrderness(Duration.ofSeconds(2)) //2000ms
                        .withTimestampAssigner(new SerializableTimestampAssigner<OrdersBean>() {
                            @Override
                            public long extractTimestamp(OrdersBean element, long recordTimestamp) {
                                return element.getTs();
                            }
                        })
        );
        // 开时间窗口 使用事件时间语义  每10s计算一次最近10s的订单总额
        AllWindowedStream<OrdersBean, TimeWindow> windowed = beansWithWM.windowAll(TumblingEventTimeWindows.of(Time.seconds(10)));
        // 窗口内部聚合 全局聚合
        SingleOutputStreamOperator<OrdersBean> res = windowed.sum("money");

        res.print() ;
        see.execute() ;

    }

}
