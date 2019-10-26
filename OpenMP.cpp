#include<iostream>
#include<omp.h>
#include<stdlib.h>
#include<time.h>
#include<stdio.h>

using namespace std;
void simplepmergesort(int a[], int i, int j);
void openmpbubble(int a[], int n)
{
    omp_set_num_threads(4);
    for(int i=0;i<n;i++)
    {
        if(i%2==0)
        {
            #pragma omp parallel for
            for(int j=0;j<n;j++)
            {
                if(j+1<n && a[j]>a[j+1])
                {
                    int temp=a[j+1];
                    a[j+1]=a[j];
                    a[j]=temp;
                }
            }
        }
        else
        {
           #pragma omp parallel for
            for(int j=1;j<n-1;j++)
            {
                if(j+1<n && a[j]>a[j+1])
                {
                    int temp=a[j+1];
                    a[j+1]=a[j];
                    a[j]=temp;
                }
            } 
        }
    }
}

void simplebubble(int a[], int n)
{
    for(int i=0;i<n;i++)
    {
        for(int j=0;j<n-1;j++)
        {
            if(a[j]>a[j+1])
            {
                int temp=a[j+1];
                a[j+1]=a[j];
                a[j]=temp;
            }
        }
   }     
}

void merge(int a[], int imin, int imax, int jmin, int jmax)
{
    int m = imin;
    int n = jmin;
    int k=0, temp[60000];
    while(m<=imax && n<=jmax)
    {
        if(a[m]>a[n])
        {
            temp[k]= a[n++];
        }
        else
        {
            temp[k]= a[m++];
        }
        k++;
    } 
    while(m<=imax)
    {
        temp[k++]= a[m++];
    }
    while(n<=jmax)
    {
        temp[k++]= a[n++];
    }
    for(m=imin,n=0;m<jmax;m++,n++)
    {
        a[m]=temp[n];
    }
}

void openmpmergesort(int a[], int i, int j)
{
   int mid;
    
    if(i<j)
    {
	if((j-i)>1000)
	{
	      mid= (i+j)/2;
	      
	      #pragma omp parallel sections
	      {
		  #pragma omp section
		  {
		    openmpmergesort(a, i, mid);
		  }
		  #pragma omp section
		  {
		    openmpmergesort(a, mid+1, j);
		  }   
	      }
		merge(a,i,mid,mid+1,j); 
	}
	else
	{
		simplepmergesort(a, i, j);
	}
    }
}

void simplepmergesort(int a[], int i, int j)
{
    int mid;  
    if(i<j)
    {
      mid= (i+j)/2;      
      simplepmergesort(a, i, mid);
      simplepmergesort(a, mid+1, j);   
      merge(a,i,mid,mid+1,j); 
    }   
}

int main()
{
    int n=60000;
   int a[n], b[n];
    double startmp, start, endmp, end;
    cout<<"\n==================Bubble Sort===================";
     for(int i=0;i<n;i++)
    {
        a[i] = rand()%n +1;
        b[i]=a[i];
    }
    /*cout<<"Initial Array-:";
    for(int i=0;i<n;i++)
    {
        cout<<a[i]<<" ";
    }*/
    cout<<"\nSorted with OpenMp: ";
    startmp = omp_get_wtime();
    openmpbubble(a,n);
    endmp = omp_get_wtime();
   
    /*for(int i=0;i<n;i++)
    {
        cout<<a[i]<<" ";
    }*/
    printf("\nTime Taken: %lf",endmp-startmp);
    cout<<"\nSorted with Simple: ";
    start = omp_get_wtime();
    simplebubble(b,n);
    end = omp_get_wtime();
    /*for(int i=0;i<n;i++)
    {
        cout<<b[i]<<" ";
    }*/
    printf("\nTime Taken: %lf",end-start);
    
  cout<<"\n==================Merge Sort===================";
  for(int i=0;i<n;i++)
    {
        a[i] = rand()%n +1;
        b[i]=a[i];
    }
    /*cout<<"Initial Array-:";
    for(int i=0;i<n;i++)
    {
        cout<<a[i]<<" ";
    }*/
    cout<<"\nSorted with OpenMp: ";
    startmp = omp_get_wtime();
    openmpmergesort(a,0,n-1);
    endmp = omp_get_wtime();
   
    /*for(int i=0;i<n;i++)
    {
        cout<<a[i]<<" ";
    }*/
    printf("\nTime Taken: %lf",endmp-startmp);
    cout<<"\nSorted with Simple: ";
    start = omp_get_wtime();
    simplepmergesort(b,0,n-1);
    end = omp_get_wtime();
    /*for(int i=0;i<n;i++)
    {
        cout<<b[i]<<" ";
    }*/
    printf("\nTime Taken: %lf \n",end-start);
}


