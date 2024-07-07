package com.taotao.cloud.flink.doe.beans.windows;


import com.taotao.cloud.flink.doe.beans.OrdersBean;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.*;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.windows.GlobalWindow;


/**
 * @Date: 2024/1/2
 * @Author: Hang.Nian.YY
 * @WX: 17710299606
 * @Tips: 学大数据 ,到多易教育
 * @DOC: https://blog.csdn.net/qq_37933018?spm=1000.2115.3001.5343
 * @Description: 不keyBy  全局chk
 * keyBy后  计数窗口
 */
public class WindowsFunctions03 {
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
        // 对数据进行分组  按照城市
        KeyedStream<OrdersBean, String> keyed = beans.keyBy(new KeySelector<OrdersBean, String>() {
            @Override
            public String getKey(OrdersBean value) throws Exception {
                return value.getCity();
            }
        });
        // 滚动计数窗口   大小为3  以分组内部为统计口径
        WindowedStream<OrdersBean, String, GlobalWindow> windowedStream = keyed.countWindow(3);

        windowedStream.sum("money").print() ;


        see.execute();

    }
}
