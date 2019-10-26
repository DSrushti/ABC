%%cu
#include<stdio.h>
#include<time.h>
void intarr(int* arr,int N);
void printarr(int* arr,int N);
__global__ 
void cal(int *arr,int N)
{
    int tid = threadIdx.x;
    int no_threads = blockDim.x;
    //printf("tid %d\n",tid);
    //printf("no %d\n",no_threads);
    int step =1;
    while(no_threads>0)
    {
        
        //printf("tid %d\n",tid);
        if(tid<no_threads)
        {
            
            int f = tid*step*2;
            int s = f + step;
            arr[f] += arr[s];
        }
        no_threads>>=1;
        step<<=1;
    }
}
__global__
void maxcal(int *arr,int N)
{
    int tid = threadIdx.x;
    int no_threads = blockDim.x;
    //printf("tid %d\n",tid);
    //printf("no %d\n",no_threads);
    int step =1;
    while(no_threads>0)
    {
        
        //printf("tid %d\n",tid);
        if(tid<no_threads)
        {
            
            int f = tid*step*2;
            int s = f + step;
            if(arr[f]<arr[s])
              arr[f] = arr[s];
        }
        no_threads>>=1;
        step<<=1;
    }
}

__global__
void stdcal(int *arr,int N,int avg)
{
    int tid = threadIdx.x;
    int no_threads = blockDim.x;
    //printf("tid %d\n",tid);
    //printf("no %d\n",no_threads);
    int step =1;
    int f = tid*step*2;
    int s = f + step;
    arr[f] = (arr[f] - avg)*(arr[f] - avg);
    arr[s] = (arr[s] - avg)*(arr[s] - avg);
}


int main()
{
    srand(time(NULL));
    int* a;
    int* d_a;
    const int N = 4;
    const int size = sizeof(a)*N;
    a = (int *)malloc(size);
    intarr(a,N);
    printf("Initial\n");
    printarr(a,N);
    cudaMalloc(&d_a,size);
    cudaMemcpy(d_a,a,size,cudaMemcpyHostToDevice);
    cal<<<1,N/2>>>(d_a,N);
    cudaMemcpy(a,d_a,size,cudaMemcpyDeviceToHost);
    int sum = a[0];    
    printf("Final sum %d\n",a[0]);
    printf("Final avg %d\n",sum/N);
    //maxcal<<<1,N/2>>>(d_a,N);
    cudaMemcpy(d_a,a,size,cudaMemcpyHostToDevice);
    stdcal<<<1,N/2>>>(d_a,N,sum/N);
    cudaMemcpy(a,d_a,size,cudaMemcpyDeviceToHost);
    printf("Final\n");
    printarr(a,N);
}

void intarr(int* arr,int N)
{
 for(int i=0;i<N;i++)
    {
        arr[i] = rand()%N;
    }
}
void printarr(int* arr,int N)
{
    for(int i=0;i<N;i++)
    {
        printf("%d \n",arr[i]);
    }
}
