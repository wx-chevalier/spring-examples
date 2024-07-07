package com.taotao.cloud.flink.doe.state;


import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.common.restartstrategy.RestartStrategies;
import org.apache.flink.api.common.state.ReducingState;
import org.apache.flink.api.common.state.ReducingStateDescriptor;
import org.apache.flink.api.common.time.Time;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.ProcessFunction;
import org.apache.flink.util.Collector;

/**
 * @Date: 2024/1/5
 * @Author: Hang.Nian.YY
 * @WX: 17710299606
 * @Tips: 学大数据 ,到多易教育
 * @DOC: https://blog.csdn.net/qq_37933018?spm=1000.2115.3001.5343
 * @Description: 自己模拟状态   不具备容错
 */
public class KeyedStageOrderTotal05 {
    public static void main(String[] args) throws Exception {

        Configuration conf = new Configuration();
        conf.setInteger("rest.port", 8888);
        StreamExecutionEnvironment see = StreamExecutionEnvironment.createLocalEnvironmentWithWebUI(conf);
        see.setParallelism(2);

        // 重启策略
        see.setRestartStrategy(RestartStrategies.fixedDelayRestart(3, Time.seconds(10)));
        // checkpoint开启
        see.enableCheckpointing(5000);

        DataStreamSource<String> data = see.socketTextStream("doe01", 8899);
        // city,category,money
        SingleOutputStreamOperator<Tuple3<String,String, Double>> cityCategoryMoney = data.map(new MapFunction<String, Tuple3<String,String, Double>>() {
            @Override
            public Tuple3<String,String, Double> map(String value) throws Exception {
                String[] split = value.split(",");
                return Tuple3.of(split[0],split[1] , Double.parseDouble(split[2]));
            }
        });
        // 分组
        KeyedStream<Tuple3<String, String, Double>, String> keyed = cityCategoryMoney.keyBy(new KeySelector<Tuple3<String, String, Double>, String>() {
            @Override
            public String getKey(Tuple3<String, String, Double> value) throws Exception {
                return value.f0;
            }
        });
       //每个城市的订单总额
        keyed.process(new ProcessFunction<Tuple3<String, String, Double>, Tuple2<String, Double>>() {

            ReducingState<Double> reducingState ;
            @Override
            public void processElement(Tuple3<String, String, Double> value, ProcessFunction<Tuple3<String, String, Double>, Tuple2<String, Double>>.Context ctx, Collector<Tuple2<String, Double>> out) throws Exception {
                // 将每个订单的金额交给状态  状态根据reduce的计算逻辑计算数据  (_+_)   100  -->100   (100+100)+100)+100
                reducingState.add(value.f2);
                // get  获取计算的结果
                out.collect(Tuple2.of(value.f0 ,   reducingState.get()));
            }

            @Override
            public void open(Configuration parameters) throws Exception {
                /**
                 * 参数1  名字
                 * 参数2  聚合函数逻辑
                 * 参数3  返回值数据类型
                 */
                ReducingStateDescriptor red =  new ReducingStateDescriptor<Double>("redu", new ReduceFunction<Double>() {
                    @Override
                    public Double reduce(Double value1, Double value2) throws Exception {
                        return value1+value2;
                    }
                } , TypeInformation.of(Double.class)) ;

                reducingState  = getRuntimeContext().getReducingState(red);
            }

            @Override
            public void close() throws Exception {
                super.close();
            }
        }).print() ;


        see.execute();

    }
}
