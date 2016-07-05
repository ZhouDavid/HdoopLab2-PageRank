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
import my_output_format.MyOutputFormat;
import myMapReduce.*;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.examples.MultiFileWordCount;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.TestNoJobSetupCleanup;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;


public class PageRanker {
    public static void main(String[] args)throws Exception{
        System.setProperty("hadoop.home.dir","E:\\share\\yarn\\hadoop-2.7.1");
        Configuration conf = new Configuration();
        String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
        if (otherArgs.length < 2) {
            System.err.println("Usage: pagerank <in> [<in>...] <out>");
            System.exit(2);
        }
        //job1
        Job job1 = Job.getInstance(conf, "preprocess");
        //job1.setNumReduceTasks(1);
        job1.setJarByClass(PageRanker.class);
        job1.setMapperClass(XmlFormatMapper.class);
        //job.setCombinerClass(XmlFormatReducer.class);
        job1.setReducerClass(XmlFormatReducer.class);
        job1.setInputFormatClass(MyInputFormat.class);
        job1.setOutputFormatClass(MyOutputFormat.class);
        job1.setOutputKeyClass(Text.class);
        job1.setOutputValueClass(Text.class);
        for (int i = 0; i < otherArgs.length - 1; ++i) {
            FileInputFormat.addInputPath(job1, new Path(otherArgs[i]));
        }
        FileOutputFormat.setOutputPath(job1,
                new Path(otherArgs[otherArgs.length - 1]));
        job1.waitForCompletion(true);
        //job2
        String inputPath = "output\\output";
        String outputPath = "output\\output0";
        int Iter_time = 30;
        Job[] jobs = new Job[Iter_time+1];

        for(Integer i =1;i<=Iter_time;i++){
            jobs[i] = Job.getInstance(conf,"rank");
            jobs[i].setJarByClass(PageRanker.class);
            jobs[i].setMapperClass(RankMapper.class);
            jobs[i].setReducerClass(RankReducer.class);
            jobs[i].setOutputFormatClass(MyOutputFormat.class);
            jobs[i].setOutputKeyClass(Text.class);
            jobs[i].setOutputValueClass(Text.class);
            FileInputFormat.addInputPath(jobs[i],new Path(inputPath));
            FileOutputFormat.setOutputPath(jobs[i],new Path(outputPath));
            inputPath = outputPath+"\\part-r-00000";
            outputPath = "output\\output"+i.toString();
            while(!jobs[i].waitForCompletion(true)){}
        }
        System.exit(jobs[Iter_time].waitForCompletion(true)?0 : 1);

    }
}
