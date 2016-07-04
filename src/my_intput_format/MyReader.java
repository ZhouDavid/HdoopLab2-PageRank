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
import org.apache.hadoop.mapreduce.v2.app.job.event.TaskAttemptEventType;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
/**
 * Created by ZhouDavid on 2016/7/4.
 */
public class MyReader extends RecordReader<Text,Text>{
    public MyReader(){}
    @Override
    public void initialize(InputSplit split, TaskAttemptContext context)
            throws IOException,InterruptedException{

    }

    @Override
    public boolean nextKeyValue()throws IOException,InterruptedException{
        return true;
    }

    @Override
    public Text getCurrentKey()throws IOException,InterruptedException{
        return new Text();
    }

    @Override
    public Text getCurrentValue()throws IOException,InterruptedException{
        return  new Text();
    }

    @Override
    public void close() throws IOException{

    }

    @Override
    public float getProgress()throws IOException,InterruptedException{
        return 0;
    }
}
