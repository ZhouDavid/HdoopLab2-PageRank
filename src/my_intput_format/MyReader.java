package my_intput_format;

import java.io.IOException;
import java.util.StringTokenizer;


import org.apache.commons.io.IOExceptionWithCause;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.lib.input.LineRecordReader;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.mapreduce.v2.app.job.event.TaskAttemptEventType;
import org.apache.hadoop.util.LineReader;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
/**
 * Created by ZhouDavid on 2016/7/4.
 */
public class MyReader extends RecordReader<Text,Text>{
    public Text title;
    public Text link_list;
    public Text line;
    public LineReader in;
    public Text content;
    public MyReader(){}
    @Override
    public void initialize(InputSplit input, TaskAttemptContext context)
            throws IOException,InterruptedException{
        FileSplit split = (FileSplit)input;
        Configuration job = context.getConfiguration();
        Path fpath = split.getPath();
        FileSystem fs = fpath.getFileSystem(job);
        FSDataInputStream fin = fs.open(fpath);
        in = new LineReader(fin,job);
        line = new Text();
        title = new Text();
        link_list = new Text();
        content = new Text();
    }

    @Override
    public boolean nextKeyValue()throws IOException,InterruptedException{
        line.clear();
        content.clear();
        title.clear();
        int linesize = in.readLine(line);
        if(linesize <=0) return false;
        content = new Text(line);
        while(!line.equals("</page>")){
            content.append(line.getBytes(),0,line.getLength());
            if(line.toString().startsWith("<title>")){
                abstractTitle();
            }
            linesize = in.readLine(line);
            if(linesize<=0)return false;
        }
        abstractLinks();
        return true;
    }

    @Override
    public Text getCurrentKey()throws IOException,InterruptedException{
        return this.title;
    }

    @Override
    public Text getCurrentValue()throws IOException,InterruptedException{
        return this.link_list;
    }

    @Override
    public void close() throws IOException{

    }

    @Override
    public float getProgress()throws IOException,InterruptedException{
        return 0;
    }

    public void abstractTitle(){
        int end = line.find("</text>");
        int len = end-7;
        title = new Text(line.toString().substring(7,len));
    }

    public void abstractLinks(){
        int start=0;
        int end = -2;
        while(end!=-1){
            start = content.find("[[",end+2);
            end = content.find("]]",start);
            if(start==-1) break;
            link_list.append(content.toString().substring(start+2,end).getBytes(),0,(end-start-2));
            link_list.append(("+").getBytes(),0,1);
        }
    }
}
