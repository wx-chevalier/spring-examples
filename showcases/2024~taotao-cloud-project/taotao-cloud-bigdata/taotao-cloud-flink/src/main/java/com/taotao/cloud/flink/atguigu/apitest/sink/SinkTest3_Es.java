package com.taotao.cloud.flink.atguigu.apitest.sink;

import com.taotao.cloud.flink.atguigu.apitest.beans.SensorReading;
import java.util.ArrayList;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.http.HttpHost;
// import org.apache.flink.streaming.connectors.elasticsearch.ElasticsearchSinkFunction;
// import org.apache.flink.streaming.connectors.elasticsearch.RequestIndexer;
// import org.apache.flink.streaming.connectors.elasticsearch6.ElasticsearchSink;
// import org.elasticsearch.action.index.IndexRequest;
// import org.elasticsearch.client.Requests;


public class SinkTest3_Es {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

        // 从文件读取数据
        DataStream<String> inputStream = env.readTextFile("D:\\Projects\\BigData\\FlinkTutorial\\src\\main\\resources\\sensor.txt");

        // 转换成SensorReading类型
        DataStream<SensorReading> dataStream = inputStream.map(line -> {
            String[] fields = line.split(",");
            return new SensorReading(fields[0], new Long(fields[1]), new Double(fields[2]));
        });

        // 定义es的连接配置
        ArrayList<HttpHost> httpHosts = new ArrayList<>();
        httpHosts.add(new HttpHost("localhost", 9200));

        // dataStream.addSink(new ElasticsearchSink.Builder<SensorReading>(httpHosts, new MyEsSinkFunction()).build());

        env.execute();
    }

    // 实现自定义的ES写入操作
    // public static class MyEsSinkFunction implements ElasticsearchSinkFunction<SensorReading>{
    //     @Override
    //     public void process(SensorReading element, RuntimeContext ctx, RequestIndexer indexer) {
    //         // 定义写入的数据source
    //         HashMap<String, String> dataSource = new HashMap<>();
    //         dataSource.put("id", element.getId());
    //         dataSource.put("temp", element.getTemperature().toString());
    //         dataSource.put("ts", element.getTimestamp().toString());
	//
    //         // 创建请求，作为向es发起的写入命令
    //         IndexRequest indexRequest = Requests.indexRequest()
    //                 .index("sensor")
    //                 .type("readingdata")
    //                 .source(dataSource);
	//
    //         // 用index发送请求
    //         indexer.add(indexRequest);
    //     }
    // }
}
