package Converter;
import java.awt.Desktop;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.alibaba.fastjson.JSON;

public class StackConverter {
	
	/**
	 * The rootNode of the stack Json data structure
	 */
	private StackFrameNode rootNode;	
	
	/**
	 * Create Stacks(similar to pstack output) to Json format Converter
	 */
	public StackConverter () {
		rootNode = new StackFrameNode ("all", 0);

	}
	
	/**
	 * Convert  callstacks to Json format
	 * @param threadStacks the list threads stacks with their call stacks. Since each row the provided stack file
	 * 		  are different threads, thus the occurance of each line is just 1 
	 * @return the Json String 
	 */
	public String ConvertStacklistToJson(List<ArrayList<String>> threadStacks) {
		if (threadStacks == null || threadStacks.size() <1) {
			return null;
		}
		rootNode = new StackFrameNode ("all", 0);
		for (ArrayList<String> thread : threadStacks) {
			StackFrameNode workingNode = this.rootNode;
			if (workingNode == null) {
				System.out.print("unexpected result: adding threads stack with name " + thread.get(0) +" failed");
				continue;
			}
			int depth = 0;
			for (String stackFrame : thread) {
				depth ++;
				if (depth == thread.size()) {
				 workingNode = workingNode.updateChildFrameNodeCount(stackFrame, 1);
				} else {
					workingNode = workingNode.updateChildFrameNodeCount(stackFrame, 0);
				}
			}
		}		
		String jsonStr = JSON.toJSONString(rootNode, false);
		return jsonStr;
		
	}
	
	/**
	 * Covert a list of threads call stacks with number of occurrences to Json format
	 * @param threadStacks the list threads stacks with their call stacks
	 * @param values the number of occurrences of the thread call stack
	 * @return
	 */
	public String ConvertlistToJson(List<ArrayList<String>> threadStacks, List<Long> values) {
		if (threadStacks == null || threadStacks.size() <1 || threadStacks.size()!=values.size()) {
			return null;
		}
		rootNode = new StackFrameNode ("all", 0);
		int threadNum= 0;
		for (ArrayList<String> thread : threadStacks) {
			Long threadCount = values.get(threadNum);
			StackFrameNode workingNode = this.rootNode;
			if (workingNode == null) {
				System.out.print("unexpected result: adding threads stack with name " + thread.get(0) +" failed");
				continue;
			}
			int depth = 0;
			for (String stackFrame : thread) {
				depth ++;
				if (depth == thread.size()) {
				 workingNode = workingNode.updateChildFrameNodeCount(stackFrame, threadCount);
				} else {
					workingNode = workingNode.updateChildFrameNodeCount(stackFrame, 0);
				}
			}
			threadNum++;
		}		
		String jsonStr = JSON.toJSONString(rootNode, false);
		return jsonStr;
		
	}
	/**
	 * main method	
	 * @param args
	 * @throws IOException
	 */
	public static void main (String []args) throws IOException {
		//exmaple to show the usage of stackConverter
		StackConverter test = new StackConverter ();
		
		//create a list similar to threads callstacks.
		ArrayList<String> a = new ArrayList<String>();
		List<ArrayList<String>> threads = new ArrayList<ArrayList<String>> ();
		a.add("a");a.add("b");a.add("c");a.add("d");a.add("e");
		threads.add(a);
		ArrayList<String> b = new ArrayList<String>();
		b.add("b");b.add("c"); b.add("a");
		threads.add(b);
		ArrayList<String> c = new ArrayList<String>();
		c.add("c"); c.add("b");c.add("a");
		threads.add(c);
		ArrayList<String> d = new ArrayList<String>();
		d.add("a");d.add("b");d.add("c");
		threads.add(d);
		
		//create a writer for writing the HTML writer
		FlameGraphHtmlWriter writer = new FlameGraphHtmlWriter("");
		writer.writeFlameJSTemPlateAsSinglePage(test.ConvertStacklistToJson(threads), false);
		try {
			Desktop.getDesktop().open(writer.getHtmlFile());
		} catch (Throwable t) {
			System.out.println(t.getLocalizedMessage());
		}
		 
	}
}