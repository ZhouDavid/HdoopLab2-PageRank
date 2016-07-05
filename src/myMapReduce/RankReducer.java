package myMapReduce;


import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * Created by ZhouDavid on 2016/7/5.
 */
public class RankReducer extends Reducer<Text,Text,Text,Text>{
    private Double pr=new Double(0);
    private Text linkTitles=new Text();
    public void reduce (Text key, Iterable<Text> values, Context context)
            throws IOException, InterruptedException {
        pr=0.0;
        linkTitles.clear();
        //System.err.println("REDUCE:"+key.toString());
        for(Text value:values){
            String v = value.toString();
            if(v.startsWith("@")){//是浮点数
                String tmp = v.substring(1,v.length());//此处可能有bug
                pr+=Double.parseDouble(tmp);
            }
            else{//是link_title

                linkTitles = new Text(value);
            }
        }
        String PR = "@"+pr.toString();
        linkTitles.append(PR.getBytes(),0,PR.length());
        context.write(key, linkTitles);
    }
}
