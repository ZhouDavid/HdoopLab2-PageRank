/**
 * Created by Zhou Jianyu on 2016/7/4.
 */


import java.io.*;
import java.util.StringTokenizer;
import java.util.Vector;
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
import edu.jhu.nlp.wikipedia.*;
public class PageRanker {
    public static void main(String[] args)throws Exception{
        System.setProperty("hadoop.home.dir","E:\\share\\yarn\\hadoop-2.7.1");
        Configuration conf = new Configuration();
        String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
        if (otherArgs.length < 3) {
            System.err.println("Usage: pagerank <preProcess/rank> <in> [<in>...] <out>");
            System.exit(2);
        }
//        //job1
//        Job job1 = Job.getInstance(conf, "preprocess");
//        //job1.setNumReduceTasks(1);
//        job1.setJarByClass(PageRanker.class);
//        job1.setMapperClass(XmlFormatMapper.class);
//        //job.setCombinerClass(XmlFormatReducer.class);
//        job1.setReducerClass(XmlFormatReducer.class);
//        job1.setInputFormatClass(MyInputFormat.class);
//        job1.setOutputFormatClass(MyOutputFormat.class);
//        job1.setOutputKeyClass(Text.class);
//        job1.setOutputValueClass(Text.class);
//        for (int i = 0; i < otherArgs.length - 1; ++i) {
//            FileInputFormat.addInputPath(job1, new Path(otherArgs[i]));
//        }
//        FileOutputFormat.setOutputPath(job1,
//                new Path(otherArgs[otherArgs.length - 1]));
//        job1.waitForCompletion(true);

        if(otherArgs[0].equals("preProcess")){
            System.err.println("preProcessing");
            String inputPath = new String(otherArgs[1]);
            FileInputStream fin = new FileInputStream(inputPath);

            while(fin.readline){

            }
            String title;Vector<String> links;
            FileOutputStream fout = new FileOutputStream(outputPath);
            try{
                long heapsize=Runtime.getRuntime().totalMemory();
                System.out.print(heapsize);
                wxp.parse();
                WikiPageIterator it = wxp.getIterator();
                while(it.hasMorePages()){

                    WikiPage page = it.nextPage();
                    title = page.getTitle();
                    links = page.getLinks();
                    System.out.println(title);
                    title+=">>";
                    fout.write(title.getBytes());
                    for(int i =0;i<links.size();i++){
                        String link = links.get(i)+"|||";
                        fout.write(link.getBytes());
                    }
                    fout.write(("0.15\r\n").getBytes());
                }
                fout.close();
            }catch(Exception e){
                e.printStackTrace();
            }



        }
        else if(otherArgs[0].equals("rank")){
            System.err.println("Ranking");
        }
        else{
            System.err.println("Usage: pagerank <preProcess/rank> <in> [<in>...] <out>");
        }
//        //job2
//        String inputPath = "output\\output";
//        String outputPath = "output\\output0";
//        int Iter_time = 30;
//        Job[] jobs = new Job[Iter_time+1];
//
//        for(Integer i =1;i<=Iter_time;i++){
//            System.err.println(i.toString()+"th times");
//            jobs[i] = Job.getInstance(conf,"rank");
//            jobs[i].setJarByClass( PageRanker.class);
//            jobs[i].setMapperClass(RankMapper.class);
//            jobs[i].setReducerClass(RankReducer.class);
//            jobs[i].setOutputFormatClass(MyOutputFormat.class);
//            jobs[i].setOutputKeyClass(Text.class);
//            jobs[i].setOutputValueClass(Text.class);
//            FileInputFormat.addInputPath(jobs[i],new Path(inputPath));
//            FileOutputFormat.setOutputPath(jobs[i],new Path(outputPath));
//            inputPath = outputPath+"\\part-r-00000";
//            outputPath = "output\\output"+i.toString();
//            while(!jobs[i].waitForCompletion(true)){}
//        }
        //System.exit(job1.waitForCompletion(true)?0 : 1);
    }
}
