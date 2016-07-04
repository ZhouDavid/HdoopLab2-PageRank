package my_intput_format;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.commons.io.IOExceptionWithCause;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

/**
 * Created by ZhouDavid on 2016/7/4.
 */
public class MyInputFormat extends FileInputFormat<Text,Text>{
    public MyInputFormat(){}

    @Override
    public RecordReader<Text,Text>createRecordReader(InputSplit split, TaskAttemptContext context)
            throws IOException,InterruptedException{
        return new MyReader();
    }
}
