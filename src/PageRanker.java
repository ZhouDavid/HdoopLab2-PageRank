/**
 * Created by Zhou Jianyu on 2016/7/4.
 */


import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.StringTokenizer;
import java.util.logging.XMLFormatter;

import my_intput_format.MyInputFormat;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.examples.MultiFileWordCount;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;


public class PageRanker {
    public static class XmlFormatMapper extends Mapper<Text,Text,Text,Text>{
        public void map(Text title, Text link_list, Context context) throws IOException, InterruptedException {
            System.err.println(title+":"+link_list);
            context.write(title,link_list);
        }
    }

    public static class XmlFormatReducer extends Reducer<Text,Text,Text,Text>{
        public void reduce(Text key, Iterable<Text> values, Context context)
                throws IOException, InterruptedException {
            for(Text value:values){
                Text link_list = new Text(value);
                context.write(key,link_list);
            }
        }
    }

    public static void main(String[] args)throws Exception{
        System.setProperty("hadoop.home.dir","E:\\share\\yarn\\hadoop-2.7.1");
        Configuration conf = new Configuration();
        String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
        if (otherArgs.length < 2) {
            System.err.println("Usage: pagerank <in> [<in>...] <out>");
            System.exit(2);
        }
        Job job = Job.getInstance(conf, "pagerank");
        job.setJarByClass(PageRanker.class);
        job.setMapperClass(XmlFormatMapper.class);
        job.setCombinerClass(XmlFormatReducer.class);
        job.setReducerClass(XmlFormatReducer.class);
        job.setInputFormatClass(MyInputFormat.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);
        job.getConfiguration().setStrings("mapreduce.reduce.shuffle.memory.limit.percent", "0.01");
        for (int i = 0; i < otherArgs.length - 1; ++i) {
            FileInputFormat.addInputPath(job, new Path(otherArgs[i]));
        }
        FileOutputFormat.setOutputPath(job,
                new Path(otherArgs[otherArgs.length - 1]));
        System.exit(job.waitForCompletion(true) ? 0 : 1);

    }
}
