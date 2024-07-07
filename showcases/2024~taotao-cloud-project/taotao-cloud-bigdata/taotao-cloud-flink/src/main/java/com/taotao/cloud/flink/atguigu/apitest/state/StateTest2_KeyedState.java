package com.taotao.cloud.flink.atguigu.apitest.state;
import com.taotao.cloud.flink.atguigu.apitest.beans.SensorReading;
import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.api.common.state.*;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;


public class StateTest2_KeyedState {
    public static void main(String[] args) throws Exception{
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

        // socket文本流
        DataStream<String> inputStream = env.socketTextStream("localhost", 7777);

        // 转换成SensorReading类型
        DataStream<SensorReading> dataStream = inputStream.map(line -> {
            String[] fields = line.split(",");
            return new SensorReading(fields[0], new Long(fields[1]), new Double(fields[2]));
        });

        // 定义一个有状态的map操作，统计当前sensor数据个数
        SingleOutputStreamOperator<Integer> resultStream = dataStream
                .keyBy("id")
                .map( new MyKeyCountMapper() );

        resultStream.print();

        env.execute();
    }

    // 自定义RichMapFunction
    public static class MyKeyCountMapper extends RichMapFunction<SensorReading, Integer>{
        private ValueState<Integer> keyCountState;

        // 其它类型状态的声明
        private ListState<String> myListState;
        private MapState<String, Double> myMapState;
        private ReducingState<SensorReading> myReducingState;

        @Override
        public void open(Configuration parameters) throws Exception {
            keyCountState = getRuntimeContext().getState(new ValueStateDescriptor<Integer>("key-count", Integer.class, 0));

            myListState = getRuntimeContext().getListState(new ListStateDescriptor<String>("my-list", String.class));
            myMapState = getRuntimeContext().getMapState(new MapStateDescriptor<String, Double>("my-map", String.class, Double.class));
//            myReducingState = getRuntimeContext().getReducingState(new ReducingStateDescriptor<SensorReading>())
        }

        @Override
        public Integer map(SensorReading value) throws Exception {
            // 其它状态API调用
            // list state
            for(String str: myListState.get()){
                System.out.println(str);
            }
            myListState.add("hello");
            // map state
            myMapState.get("1");
            myMapState.put("2", 12.3);
            myMapState.remove("2");
            // reducing state
//            myReducingState.add(value);

            myMapState.clear();

            Integer count = keyCountState.value();
            count++;
            keyCountState.update(count);
            return count;
        }
    }
}
