package myMapReduce;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.MultipleInputs;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;
import org.apache.hadoop.util.GenericOptionsParser;

public class XmlFormatReducer extends Reducer<Text,Text,Text,Text>{
    private MultipleOutputs<Text,Text> multipleOutputs;
    public void setup(Context context)throws IOException, InterruptedException{
        multipleOutputs = new MultipleOutputs<Text, Text>(context);
    }

    public void reduce(Text key, Iterable<Text> values, Context context)
            throws IOException, InterruptedException {
        //System.err.println("reducing");
        for(Text value:values){
            Text link_list = new Text(value);
            Text init_pr = new Text("@10");
            link_list.append(init_pr.getBytes(),0,init_pr.getLength());
            System.err.println(link_list);
            context.write(key,link_list);
        }
    }
}