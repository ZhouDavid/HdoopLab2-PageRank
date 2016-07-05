package myMapReduce;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.StringTokenizer;

/**
 * Created by ZhouDavid on 2016/7/5.
 */
public class RankMapper extends Mapper<Object,Text,Text,Text>{
    private Text title;
    private Text linkTitles;
    private DoubleWritable pr;
    public void setup(Context context){
        pr = new DoubleWritable();
        title = new Text();
        linkTitles = new Text();
    }

    public void map(Object key,Text line,Context context)throws IOException, InterruptedException{
        StringTokenizer tokenizer1 =new StringTokenizer(line.toString(),"+>@");
        StringTokenizer tokenizer2 = new StringTokenizer(line.toString(),">@");
        title = new Text(tokenizer2.nextToken());
        linkTitles = new Text(tokenizer2.nextToken());
        //System.err.println("MAP:"+title.toString());
        //初始化link_titles
        final int links = tokenizer1.countTokens()-2;
        Text[] link_titles = new Text[links];
        for(int i = 0;i<links;i++){
            link_titles[i]=new Text();
        }

        int i = -1;
        while(tokenizer1.hasMoreTokens()){
            String tmp = tokenizer1.nextToken();
            if(i>-1){
                if(i==links){
                    double d = Double.parseDouble(tmp);
                    d=d/links;
                    pr.set(d);
                }
                else{
                    link_titles[i].set(tmp);
                }
            }
            i++;
        }

        for(int j = 0;j<links;j++){
            context.write(link_titles[j],new Text("@"+pr.toString()));
        }
        context.write(title,linkTitles);
    }
}
