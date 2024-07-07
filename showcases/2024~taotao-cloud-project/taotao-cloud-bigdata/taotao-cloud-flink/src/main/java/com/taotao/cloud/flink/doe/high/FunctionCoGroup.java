package com.taotao.cloud.flink.doe.high;


import org.apache.flink.api.common.eventtime.SerializableTimestampAssigner;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.functions.CoGroupFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.util.Collector;

/**
 * @Date: 2024/1/3
 * @Author: Hang.Nian.YY
 * @WX: 17710299606
 * @Tips: 学大数据 ,到多易教育
 * @DOC: https://blog.csdn.net/qq_37933018?spm=1000.2115.3001.5343
 * @Description:
 */
public class FunctionCoGroup {
    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        conf.setInteger("rest.port", 8888);
        StreamExecutionEnvironment see = StreamExecutionEnvironment.createLocalEnvironmentWithWebUI(conf);
        see.setParallelism(1);
        // uid,name,ts
        DataStreamSource<String> userStream = see.socketTextStream("doe01", 6666);
        // oid , money, uid, ts
        DataStreamSource<String> orderStream = see.socketTextStream("doe01", 7777);
        // 用户数据流
        SingleOutputStreamOperator<UserBean> userBeans = userStream.map(new MapFunction<String, UserBean>() {
            @Override
            public UserBean map(String value) throws Exception {
                String[] arr = value.split(",");
                return new UserBean(Integer.parseInt(arr[0]), arr[1], Long.parseLong(arr[2]));
            }
        });
        // 订单数据流
        SingleOutputStreamOperator<OrderBean> orderBeans = orderStream.map(new MapFunction<String, OrderBean>() {
            @Override
            public OrderBean map(String value) throws Exception {
                String[] arr = value.split(",");
                return new OrderBean(Integer.parseInt(arr[0]), Double.parseDouble(arr[1]), Integer.parseInt(arr[2]), Long.parseLong(arr[3]));
            }
        });
        // 用户数据流   分配水位线
        SingleOutputStreamOperator<UserBean> userBeanWm = userBeans.assignTimestampsAndWatermarks(WatermarkStrategy.<UserBean>forMonotonousTimestamps().withTimestampAssigner(new SerializableTimestampAssigner<UserBean>() {
            @Override
            public long extractTimestamp(UserBean element, long recordTimestamp) {
                return element.getTs();
            }
        }));
        // 订单数据流 分配水位线
        SingleOutputStreamOperator<OrderBean> orderBeanWm = orderBeans.assignTimestampsAndWatermarks(WatermarkStrategy.<OrderBean>forMonotonousTimestamps().withTimestampAssigner(new SerializableTimestampAssigner<OrderBean>() {
            @Override
            public long extractTimestamp(OrderBean element, long recordTimestamp) {
                return element.getTs();
            }
        }));


        userBeanWm.coGroup(orderBeanWm)
                .where(uBean -> uBean.getUid())
                .equalTo(oBean -> oBean.getUid())
                .window(TumblingEventTimeWindows.of(Time.seconds(10)))
                .apply(new CoGroupFunction<UserBean, OrderBean, String>() {
                    @Override
                    public void coGroup(Iterable<UserBean> first, Iterable<OrderBean> second, Collector<String> out) throws Exception {
                      // inner  join
                     /*   for (UserBean userBean : first) { // 1.1    :  2.1     3.3     4,5
                            for (OrderBean orderBean : second) {   // 1.zss   1.zss   3,ww
                                //oid , money , uid , name , ts
                                out.collect(orderBean.getOid()+","+orderBean.getMoney() +","+orderBean.getUid()+""+userBean.getName()  +","+orderBean.getTs());
                            }
                        }*/
                        // left   join
                        boolean  flag =  false ;
                        for (UserBean userBean : first) {  // user    left  join  order
                            for (OrderBean orderBean : second) {
                                out.collect(userBean.getUid() +","+userBean.getName() +","+orderBean.getOid() +","+orderBean.getMoney());
                                flag =  true ;
                            }
                            if(!flag){
                                out.collect(userBean.getUid() +","+userBean.getName() +",null, null");
                            }

                        }

                        // right   join

                    }
                });


        see.execute();


    }
}
