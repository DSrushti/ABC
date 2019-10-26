import java.util.*;
import java.lang.Math;
class Node
{
	String id;
	int val;
	Node left,right;
	Node(String id, int val)
	{
		this.id = id;
		this.val = val;
	}
}

public class ABP
{
	public static void main(String[] args)
	{
		Node tree = new Node("A",0);

		tree.left = new Node("B",0);
		tree.left.left = new Node("D",0);
		tree.left.right = new Node("E",0);
		tree.left.left.left = new Node("L1",3);
		tree.left.left.right = new Node("L2",5);
		tree.left.right.left = new Node("L3",6);
		tree.left.right.right = new Node("L4",9);

		tree.right = new Node("C",0);
		tree.right.left = new Node("F",0);
		tree.right.right = new Node("G",0);
		tree.right.left.left = new Node("L5",1);
		tree.right.left.right = new Node("L6",2);
		tree.right.right.left = new Node("L7",0);
		tree.right.right.right = new Node("L8",-1);
		
		System.out.println(cal(tree,-100000,100000,true));
	}

	static int cal(Node node,int alpha,int beta, boolean ismax)
	{
		int bestval,val;
		if(node.left == null && node.right ==null)
		{
			return node.val;
		} 
		if(ismax)
		{
			bestval = -100000;
			val = cal(node.left,alpha,beta,false);
			bestval = Math.max(bestval,val);
			alpha = Math.max(bestval,alpha);
			if(beta>alpha)
			{
				val = cal(node.right,alpha,beta,false);
				bestval = Math.max(bestval,val);
				alpha = Math.max(bestval,alpha);
			}
		}
		else
		{
			bestval = 100000;
			val = cal(node.left,alpha,beta,true);
			bestval = Math.min(bestval,val);
			beta = Math.min(bestval,beta);
			if(beta>alpha)
			{
				val = cal(node.right,alpha,beta,true);
				bestval = Math.min(bestval,val);
				beta = Math.min(bestval,beta);
			}
		}
		return bestval;
	}
}
