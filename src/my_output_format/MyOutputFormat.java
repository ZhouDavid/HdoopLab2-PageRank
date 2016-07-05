package my_output_format;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.retry.RetryPolicies;
import org.apache.hadoop.mapreduce.RecordWriter;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import java.io.IOException;

/**
 * Created by ZhouDavid on 2016/7/5.
 */
public class MyOutputFormat extends FileOutputFormat<Text,Text>{
    @Override
    public RecordWriter<Text,Text>getRecordWriter(TaskAttemptContext job)
    throws IOException,InterruptedException{
        Configuration conf = job.getConfiguration();
        Path fpath = getDefaultWorkFile(job,"");
        FileSystem fs = fpath.getFileSystem(conf);
        FSDataOutputStream fout = fs.create(fpath,false);
        return new MyWriter(fout,">>");
    }

}
