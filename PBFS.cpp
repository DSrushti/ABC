#include<iostream>
#include<mpi.h>
using namespace std;
int allv(int a[],int n)
{
	for(int j=1;j<n;j++)
	{
		if(a[j]==0)
			return 0;
	}
	return 1;
}
int main(int argc,char *argv[])
{
	int mat[] = {0,1,1,0,0,0,1,0,0,1,1,0,1,0,0,0,1,0,0,1,0,0,1,1,0,1,1,1,0,1,0,0,0,1,1,0};
	int recv[10];
	int bfs[100];
	int source = 0;
	int v[] = {1,0,0,0,0,0};
	int n = 6;
	int rank,size;
	int MAX_QUEUE_SIZE = 10;
	int queue[MAX_QUEUE_SIZE];
	MPI_Init(&argc,&argv);
	MPI_Comm_rank(MPI_COMM_WORLD,&rank);
	MPI_Comm_size(MPI_COMM_WORLD,&size);

	MPI_Bcast(&n,1,MPI_INT,0,MPI_COMM_WORLD);
	MPI_Bcast(&source,1,MPI_INT,0,MPI_COMM_WORLD);

	MPI_Scatter(mat,n,MPI_INT,recv,n,MPI_INT,0,MPI_COMM_WORLD);
	for(int i=0;i<MAX_QUEUE_SIZE;i++)
	{
		queue[i] = -1;
	}
	int index = 0;
	if(rank>=source)
	{
		for(int i=0;i<n;i++)
		{
			if(recv[i]==1)
			{
				queue[index++] = i;
			}
		}
	}
	cout<<"Process"<<rank<<" : ";
	for(int j=0;j<index;j++)
	{
		cout<<queue[j]<<"  ";
	}
	cout<<endl;
	MPI_Barrier(MPI_COMM_WORLD);
	MPI_Gather(queue,MAX_QUEUE_SIZE,MPI_INT,bfs,MAX_QUEUE_SIZE,MPI_INT,0,MPI_COMM_WORLD);
	
	for(int i = 0; i < n; i++)
	{
		v[i] = 0;
	}
	v[0] = 1;
	if(rank == 0)
	{
		cout<<"\nBFS Traversal: "<<endl;
		cout<<source;
		for(int i = 0; i < MAX_QUEUE_SIZE * n; i++)
		{
			
			if(allv(v,n))
			{
				break;
			}
			
			if(bfs[i] != -1)
			{
				//cout<<"abc"<<bfs[i]<<endl;
				if(v[bfs[i]] == 0)
				{
					cout<<" -> "<<bfs[i];
					v[bfs[i]] = 1;
				}
			}
		}
	}

	MPI_Finalize();
	return 0;
}
