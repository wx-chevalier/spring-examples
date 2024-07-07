package com.taotao.cloud.flink.doe.operator;


import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

/**
 * @Date: 2023/12/28
 * @Author: Hang.Nian.YY
 * @WX: 17710299606
 * @Tips: 学大数据 ,到多易教育
 * @DOC: https://blog.csdn.net/qq_37933018?spm=1000.2115.3001.5343
 * @Description:
 */
public class Function03Filter01 {
    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        conf.setInteger("rest.port", 8888);
        StreamExecutionEnvironment see = StreamExecutionEnvironment.createLocalEnvironmentWithWebUI(conf);

        DataStreamSource<String> ds = see.socketTextStream("doe01", 8899);
        SingleOutputStreamOperator<Integer> numbers = ds.flatMap(new FlatMapFunction<String, Integer>() {
            @Override
            public void flatMap(String value, Collector<Integer> out) throws Exception {
                    String[] arr = value.split("\\s+");
                    for (String e : arr) { // ""
                        try {
                            int number = Integer.parseInt(e);
                            out.collect(number);
                        } catch (Exception ex) {
                        }
                    }

            }
        });

        numbers.keyBy(e->"hello") ;

        SingleOutputStreamOperator<Integer> res = numbers.filter(new FilterFunction<Integer>() {
            // 每个元素  调用一次 进行判断
            // 符合条件的元素 留下
            @Override
            public boolean filter(Integer value) throws Exception {
                return (value > 3 && value % 2 == 0) ? true : false;
            }
        });

        res.print() ;
        see.execute();


    }
}
