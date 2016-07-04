/**
 * Created by Zhou Jianyu on 2016/7/4.
 */
package my_mappper;
import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

public class XmlFormatMapper extends Mapper<Object,Text,Text,Text>{
    public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        context.write(new Text("asdf"),new Text("asdf"));
    }
}
