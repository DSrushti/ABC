import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.io.*;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.fs.Path;
import java.io.IOException;
import java.util.*;

public class WORD {
	
	public static class Tokenizer extends Mapper<Object,Text,Text,IntWritable>
	{
		IntWritable o = new IntWritable(1);
		Text word = new Text();
		public void map(Object object,Text text,Context context) throws IOException, InterruptedException
		{
			StringTokenizer line = new StringTokenizer(text.toString());
			while(line.hasMoreTokens())
			{
				word.set(line.nextToken());
				context.write(word, o);
			}
		}
	}
	
	public static class MyReducer extends Reducer<Text,IntWritable,Text,IntWritable>
	{
		IntWritable o = new IntWritable();
		public void reduce(Text text,Iterable<IntWritable> input,Context context) throws IOException, InterruptedException
		{
			int sum = 0;
			for(IntWritable a : input)
			{
				sum += a.get();
			}
			o.set(sum);
			context.write(text, o);
		}
	}
	
	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException
	{
		Configuration conf = new Configuration();
		Job job = new Job(conf,"word count");
		job.setJarByClass(WORD.class);
		job.setMapperClass(Tokenizer.class);
		job.setCombinerClass(MyReducer.class);
		job.setReducerClass(MyReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		System.exit(job.waitForCompletion(true)?0:1);
	}
}
