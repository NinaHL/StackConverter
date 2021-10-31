package Converter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class StackFrameNode {
	
	/**
	 * The stack frame node name  
	 */
	private String name;
	
	/**
	 * the value of the stack value
	 */
	private long value = 0;
	
	/**
	 * HashMap for saving the children nodes of the frames 
	 */
	HashMap<String, StackFrameNode> children;
	
	/**
	 * To create the stack Frame node
	 * @param frameName
	 * @param frameValue
	 */
	public StackFrameNode(String frameName, long frameValue) {
		this.name = frameName;
		this.value = frameValue;
		children = new HashMap<String, StackFrameNode> ();		
	}
	
	/**
	 * To create the stack Frame node
	 * @param frameName
	 * @param frameValue
	 * @param childrenFrames
	 */
	public StackFrameNode(String frameName, int frameValue,  HashMap<String, StackFrameNode>  childrenFrames) {
		this.name = frameName;
		this.value = frameValue;
		this.children  = childrenFrames;
	}
	
	/**
	 * update Stack Frame count info
	 */
	public void updateStackFrameNodeCount (long addNum) {
		setValue(getValue() + addNum);
	}
	
	/** 
	 * update child Frame Graph Count 
	 * @param childName
	 * @param addNum
	 * @return
	 */
	public StackFrameNode updateChildFrameNodeCount(String childName, long addNum) {
		StackFrameNode childNode = null;
		if (children==null || children.size()<1 ||!children.containsKey(childName)) {
			childNode = new StackFrameNode(childName, addNum);
			children.put(childName, childNode);
		} else if  (children.containsKey(childName)) {
			childNode = children.get(childName);
			childNode.updateStackFrameNodeCount(addNum);			
		} 
		return childNode;
	}	

	/**
	 * Check if the Frame node exist on a node's child Frame
	 * @param childrenFrame
	 * @param childNme
	 * @return
	 */
	public int ifChildrenCotainsFrame(List <StackFrameNode> childrenFrame, String childNme) {
		for (int i =0; i< children.size()-1; i++) {
			if (childrenFrame.get(i).getName().equals(childNme)) {
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * Return the name of the stackFrame
	 * @return
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Set the name of the stack Frame
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Get the value of the Stack Frame
	 * @return
	 */
	public long getValue() {
		return value;
	}
	
	/**
	 * Set the value of Stack Frame
	 * @param value
	 */
	public void setValue(long value) {
		this.value = value;
	}
	
	/**
	 * return the children Frame Nodes
	 * @return
	 */
	public List<StackFrameNode> getChildren() {
		if(children==null || children.size()<1){
			return null;
		}
		List<StackFrameNode> result = new ArrayList<StackFrameNode>();
		result.addAll(children.values());
		return result;
	}
	
	/**
	 * Set the children of the Frame Node
	 * @param children
	 */
	public void setChildren(HashMap<String, StackFrameNode> children) {
		this.children = children;
	}

}
