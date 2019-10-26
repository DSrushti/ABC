#include<cstdio>
#include<mpi.h>
#include<iostream>
#include<chrono>
#include<unistd.h>

#include <time.h>

using namespace std::chrono;
using namespace std;

void binary_search(int a[],int start,int end,int key,int rank)
{
	while(start <= end)
	{
		int m = (start+end)/2;
        
		if(a[m]==key)
		{
			cout<<"The element is found by Process No "<<rank+1<<" at "<<m+1<<endl;
			cout<<"";
			return;
		}
		else if(a[m]<key)
		{
			start=m+1;
		}
		else
		{
			end=m-1;
		}
	}
    cout<<"Not found by Process No : "<<rank+1<<endl;
}

int main(int argc, char **argv)
{

	//cout<<"Hello welcome to MPI World"<<endl;
    int n=8000;
    int key=4500;
    double c[4];//Communicator
    int a[n];
	for(int i=0;i<n;i++)
	{
        a[i]=i+1;
	}
    
	int rank,blocksize;
	MPI_Init(&argc,&argv);//This routine must be called before any other MPI routine
	MPI_Comm_rank(MPI_COMM_WORLD,&rank);//Determines the rank of the calling process in the communicator 
	/*The default communicator is called MPI_COMM_WORLD. It basically groups all the processes when the program started. 
	The number in a communicator does not change once it is created. That number is called the size of the communicator. 
	At the same time, each process inside a communicator has a unique number to identify it. This number is called the rank of the
	process.The rank of a process always ranges from 0 to size−1.*/
	MPI_Comm_size(MPI_COMM_WORLD,&blocksize);//Determines the size of the group associated with a communicator //no.of processes in a 
	//group of communicator
	
	blocksize=n/4;

	if(rank==0)
	{
		double start = MPI_Wtime();
    
		binary_search(a,rank*blocksize,(rank+1)*blocksize-1,key,rank);

		double end = MPI_Wtime();

		cout<<"The time for process 1 is "<<(end-start)*1000<<endl<<endl;
		c[rank]=end;

	}

	else if(rank==1)
	{

		double start = MPI_Wtime();
		
		binary_search(a,rank*blocksize,(rank+1)*blocksize-1,key,rank);		

		double end = MPI_Wtime();
	
		cout<<"The time for process 2 is "<<(end-start)*1000<<endl<<endl;
		c[rank]=end;

	}

	else if(rank==2)
	{

		
		double start = MPI_Wtime();
		
		binary_search(a,rank*blocksize,(rank+1)*blocksize-1,key,rank);

		double end = MPI_Wtime();

		cout<<"The time for process 3 is "<<(end-start)*1000<<endl<<endl;
		c[rank]=end;

	}
	else if(rank==3)
	{

		
		double start = MPI_Wtime();
		
		binary_search(a,rank*blocksize,(rank+1)*blocksize-1,key,rank);

		double end = MPI_Wtime();

		cout<<"The time for process 4 is "<<(end-start)*1000<<endl<<endl;
		c[rank]=end;

	}
	
	MPI_Finalize();
	/*This routines cleans up all MPI state. 
	Once this routine is called, no MPI routine (even MPI_INIT) may be called. 
	The user must ensure that all pending communications involving a process completes before the process calls MPI_FINALIZE. */
}

