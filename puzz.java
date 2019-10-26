public class puzz{
	public static void main(String[] args)
	{
		int start[][] = {{1,8,2},{0,4,3},{7,6,5}};
		int goal[][] = {{1,2,3},{4,5,6},{7,8,0}};
		int visited[][] = {{0,0,0},{0,0,0},{0,0,0}};
		int block_i = 1;
		int block_j = 0;
		int val = 0;
		int up,down,left,right;
		String de = "None";
		System.out.println("**********start**********\n\n\n");
		for(int i=0;i<3;i++)
		{
			for(int j=0;j<3;j++)
			{
				System.out.print(start[i][j] + " " );
			}
			System.out.println();
		}
		System.out.println("**********goal**********\n\n\n");
		for(int i=0;i<3;i++)
		{
			for(int j=0;j<3;j++)
			{
				System.out.print(goal[i][j] + " " );
			}
			System.out.println();
		}
		
		while(heu(start,goal)!=0)
		{
			
			val = 100000;
			up = 100000;
			down = 100000;
			left = 100000;
			right = 100000;
			de = "None";
			if(block_i<2 && visited[block_i+1][block_j]!=1)
			{
				down = heu(swap(start,block_i,block_j,block_i+1,block_j),goal);
			}
			if(block_j<2 && visited[block_i][block_j+1]!=1)
			{
				right = heu(swap(start,block_i,block_j,block_i,block_j+1),goal);
			}
			if(block_i>0 && visited[block_i-1][block_j]!=1)
			{
				up = heu(swap(start,block_i,block_j,block_i-1,block_j),goal);
			}
			if(block_j>0 && visited[block_i][block_j-1]!=1)
			{
				left = heu(swap(start,block_i,block_j,block_i,block_j-1),goal);
			}
			if(left<val)
			{
				val = left;
				de = "left";
			}
			if(up<val)
			{
				val = up;
				de = "up";
			}
			if(down<val)
			{
				val = down;
				de = "down";
			}
			if(right<val)
			{
				val = right;
				de = "right";
			}
			for(int ai=0;ai<3;ai++)
			{
				for(int aj=0;aj<3;aj++)
				{
					visited[ai][aj] = 0;
				}
			}
			System.out.println(val);
			if(de.equals("none"))
			{
				System.out.println("Not Possible");
				break;	
			}
			else
			{
				System.out.println(de+"\n\n\n");
				switch(de)
				{
					case "up" :
					{
						
						start = swap(start,block_i,block_j,block_i-1,block_j);
						block_i--;
						break;
					}
					case "down" :
					{
						
						start = swap(start,block_i,block_j,block_i+1,block_j);
						block_i++;
						break;
					}
					case "left" :
					{
						
						start = swap(start,block_i,block_j,block_i,block_j-1);
						block_j--;
						break;
					}
					case "right" :
					{
						
						start = swap(start,block_i,block_j,block_i,block_j+1);
						block_j++;
						break;
					}
				}
				visited[block_i][block_j] = 1;
				
			}
			System.out.println("**********executing**********");
			for(int i=0;i<3;i++)
			{
				for(int j=0;j<3;j++)
				{
					System.out.print(start[i][j] + " " );
				}
				System.out.println();
			}
			
		}

	}
	static int heu(int a[][],int b[][])
	{
		int cost = 0; 
		for(int i=0;i<3;i++)
		{
			for(int j=0;j<3;j++)
			{
				if(a[i][j]!=b[i][j])
				{
					cost++;
				}
			}
		}
		return cost;
	}
	static int[][] swap(int a[][],int i,int j,int ni,int nj)
	{
		int temp[][] = {{0,0,0},{0,0,0},{0,0,0}};
		for(int ai=0;ai<3;ai++)
		{
			for(int aj=0;aj<3;aj++)
			{
				temp[ai][aj] = a[ai][aj];
			}
		}
		int x = temp[i][j];
		temp[i][j] = temp[ni][nj];
		temp[ni][nj] = x;
		return temp;
	}

}
