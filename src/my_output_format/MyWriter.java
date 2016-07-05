package my_output_format;

import com.sun.org.apache.bcel.internal.generic.NEW;
import org.apache.commons.collections.KeyValue;
import org.apache.hadoop.io.Text;

import org.apache.hadoop.mapreduce.RecordWriter;
import org.apache.hadoop.mapreduce.TaskAttemptContext;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by ZhouDavid on 2016/7/5.
 */

public class MyWriter extends RecordWriter<Text,Text> {
    protected DataOutputStream out;
    private String KeyValueSep;
    public static final String NEW_LINE = "\r\n";

    public MyWriter(DataOutputStream out, String KeyValueSep){
        this.out = out;
        this.KeyValueSep = KeyValueSep;
    }
    public MyWriter(DataOutputStream out){
        this(out,":");
    }

    @Override
    public void write(Text title,Text link_list)throws IOException,InterruptedException{
        if(title!=null){
            out.write(title.toString().getBytes());
            out.write(this.KeyValueSep.getBytes());
        }
        if(link_list!=null){
            out.write(link_list.getBytes());
            out.write("@1000".getBytes(),0,"@1000".length());
            if(NEW_LINE==null){
                System.err.println("bug's here");
            }
            else out.write(NEW_LINE.getBytes());
        }
    }

    @Override
    public void close(TaskAttemptContext context)throws IOException,InterruptedException{
        out.close();
    }
}
