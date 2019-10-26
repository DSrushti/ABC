class Node
{
	String id , on;
	Node next;
	boolean clear;
	Node()
	{
		id = "";
		on = "";
		clear = true;
		next = null;
	}
	Node(String Id,String On,boolean Clear)
	{
		id = Id;
		on = On;
		clear = Clear;
		next = null;
	}
}

public class goalstack
{
		
	public static void main(String[] args)
	{
		Node start = new Node("B","A",true);
		start.next = new Node("A","T",false);
		start.next.next = new Node("C","T",true);
		start.next.next.next = new Node("D","T",true);
		Node goal = new Node("C","A",true);
		goal.next = new Node("A","T",false);
		goal.next.next = new Node("B","D",true);
		goal.next.next.next = new Node("D","T",false);
		Node temp = start;
		/*while(temp!=null)
		{
			System.out.println(temp.id + " on " + temp.on + " Clear " + temp.clear);
			temp = temp.next;
		}
		temp = goal;
		while(temp!=null)
		{
			System.out.println(temp.id + " on " + temp.on + " Clear " + temp.clear);
			temp = temp.next;
		}*/
		temp = goal;
		while(temp!=null)
		{
			process(temp,start);
			temp = temp.next;
		}
		temp = start;
		while(temp!=null)
		{
			System.out.println(temp.id + " on " + temp.on + " Clear " + temp.clear);
			temp = temp.next;
		}
	}
	static void process(Node node,Node start)
	{
		
		Node temp = start;
		while(temp!=null)
		{
			if(node.id.equals(temp.id))
				break;
			temp = temp.next;
		}
		
		if(!(temp.on.equals(node.on) && temp.clear == node.clear))
			if(!temp.clear)
			{
				clear(temp.id,start);
			}
			clear(node.on,start);
			put(node.id,node.on,start);
	}
	static void clear(String a,Node start)
	{
		System.out.println("Process" + a);
		if(!a.equals("T"))
		{
			Node temp = start;
			while(temp!=null)
			{
				if(temp.id.equals(a))
					break;
				temp = temp.next;
			}
			if(!temp.clear)
			{
				Node temp1 = start;
				while(temp1!=null)
				{
					if(temp1.on.equals(a))
					{
						clear(temp1.id,start);
						put(temp1.id,"T",start);
						break;
					}
					temp1 = temp1.next;
				}
				temp.clear = true;	
			}
		}
	}
	static void put(String id,String on,Node start)
	{
		System.out.println("Process" + id + on);
		if(!id.equals("T"))
		{
			Node temp = start;
			while(temp!=null)
			{
				if(temp.id.equals(id))
					break;
				temp = temp.next;
			}			
			Node temp1 = start;
			while(temp1!=null)
			{
				if(temp1.id.equals(on))
					break;
				temp1 = temp1.next;
			}
			temp.on = on;
			if(temp1!=null)
				temp1.clear = false;
		}
	}
}
