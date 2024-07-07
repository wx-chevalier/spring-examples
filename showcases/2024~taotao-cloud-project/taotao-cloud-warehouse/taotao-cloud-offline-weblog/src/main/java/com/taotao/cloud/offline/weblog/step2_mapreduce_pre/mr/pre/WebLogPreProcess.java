package com.taotao.cloud.bigdata.offline.weblog.step2_mapreduce_pre.mr.pre;

import com.taotao.cloud.bigdata.offline.weblog.step2_mapreduce_pre.mrbean.WebLogBean;
import com.taotao.cloud.bigdata.offline.weblog.step2_mapreduce_pre.utils.WebLogParser;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * @ClassName: step2_mapreduce_pre.com.bigdata.log.click.mr.pre
 * @Author WebLogPreProcess
 * @Date 18-8-10
 * @Version V1.0.0
 * @Description:
 */
public class WebLogPreProcess {
    //日志预处理mr程序

    static class WebLogPreProcessMapper extends Mapper<LongWritable, Text, Text, NullWritable>{

        Set<String> pages = new HashSet<>();
        Text k = new Text();
        NullWritable v = NullWritable.get();

        @Override
        protected void setup(Context context) throws IOException, InterruptedException {
            pages.add("/statics/");
            pages.add("/js/");
            pages.add("/css/");
        }

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String lineStr = value.toString();
            WebLogBean webLogBean = WebLogParser.parser(lineStr);
            // 过滤js/图片/css等静态资源
            WebLogParser.filterStaticResource(webLogBean, pages);

            k.set(webLogBean.toString());
            context.write(k, v);
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);

        job.setJarByClass(WebLogParser.class);

        job.setMapperClass(WebLogPreProcessMapper.class);

        job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(NullWritable.class);

		FileInputFormat.setInputPaths(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));

		job.setNumReduceTasks(0);

        boolean b = job.waitForCompletion(true);
        System.exit(b?0:1);

        //运行程序
        //hadoop jar weblogpre.jar  cn.bigdata.log.click.mr.pre.WeblogPreProcess /weblog/flume-collection/input /weblog/flume-collection/output
    }
}
