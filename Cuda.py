#!pip install git+git://github.com/andreinechaev/nvcc4jupyter.git


# %load_ext nvcc_plugin
/////////////Vector Addition///////////
# Commented out IPython magic to ensure Python compatibility.
# %%cu
# #include<stdio.h>
# void inti(int *a,int N);
# void print(int *a,int N);
# __global__
# void cal(int *a,int *b,int *c)
# {
#    int tid = threadIdx.x;
#    printf("tid %d\n",tid);
#    c[tid] = a[tid] + b[tid];
# }
# int main()
# {
#     const int N = 4;
#     const int size = sizeof(int)*N;
#     int *a, *d_a;
#     a = (int *)malloc(size);
#     inti(a,N);
#     print(a,N);
#     
#     int *b,*d_b;
#     b = (int *)malloc(size);
#     inti(b,N);
#     print(b,N);
#     
#     int *c,*d_c;
#     c = (int *)malloc(size);
#     
#     
#     cudaMalloc(&d_a,size);
#     cudaMemcpy(d_a,a,size,cudaMemcpyHostToDevice);
#     
#     cudaMalloc(&d_b,size);
#     cudaMemcpy(d_b,b,size,cudaMemcpyHostToDevice);
#     
#     cudaMalloc(&d_c,size);
#     cudaMemcpy(d_c,c,size,cudaMemcpyHostToDevice);
#     
#     cal<<<1,N>>>(d_a,d_b,d_c);
#     cudaMemcpy(c,d_c,size,cudaMemcpyDeviceToHost);
#     print(c,N);
#     
# }
# void inti(int *a,int N)
# {
#     for(int i=0;i<N;i++)
#     {
#         a[i] = rand()%N;
#     }
# }
# 
# void print(int *a,int N)
# {
#     for(int i=0;i<N;i++)
#     {
#         printf("%d",a[i]);
#     }
#     printf("\n");
# }


////////////////Matrix Mul///////////////////
# Commented out IPython magic to ensure Python compatibility.
# %%cu
# #include<stdio.h>
# void inti(int *a,const int M,const int N)
# {
#     for(int i=0;i<M;i++)
#     {
#         for(int j=0;j<N;j++)
#         {
#             if((i*M+j)<M*N)
#             {
#               a[i*M + j] = rand()%(N);
#             }
#         }
#     }
# }
# 
# void print(int *a,const int M,const int N)
# {
#     for(int i=0;i<M;i++)
#     {
#         for(int j=0;j<N;j++)
#         {
#             if((i*M+j)<M*N)
#             {
#               printf("%d\t",a[i*M + j]);
#             }
#         }
#         printf("\n");
#     }
# }
# 
# 
# __global__
# void cal(int* a,int* b,int* c,int M,int N,int P)
# {
#      int row = blockIdx.y*blockDim.y + threadIdx.y;
#      int col = blockIdx.x*blockDim.x + threadIdx.x;
#      //printf("blockIndex.x = %d\n",blockIdx.x);
#      //printf("blockIndex.y = %d\n",blockIdx.y);
#      //printf("blockDim.x = %d\n",blockDim.x);
#      //printf("blockDim.y = %d\n",blockDim.y);
#      int  sum=0;
# 
#      if(col<P && row<M)
#      {
#         for(int i=0;i<N;i++)
#          {
#             sum += a[row*N+i]*b[i*N+col];
#          }
#         c[row*P + col]=sum;
#      }
# }
# 
# int main()
# {
#     const int M = 4;
#     const int N = 4;
#     const int sizea = sizeof(int)*M*N;
#     int *a, *d_a;
#     a = (int *)malloc(sizea);
#     printf("******A*******\n");    
#     inti(a,M,N);
#     print(a,M,N);
#     cudaMalloc(&d_a,sizea);
#     cudaMemcpy(d_a,a,sizea,cudaMemcpyHostToDevice);
#     
#    
#     const int P = 4;
#     const int sizeb = sizeof(int)*P*N;
#     int *b, *d_b;
#     b = (int *)malloc(sizeb);
#     printf("******B*******\n");    
#     inti(b,M,N);
#     print(b,M,N);
#     cudaMalloc(&d_b,sizeb);
#     cudaMemcpy(d_b,b,sizeb,cudaMemcpyHostToDevice);
#     
#     
#     const int sizec = sizeof(int)*M*P;
#     int *c, *d_c;
#     c = (int *)malloc(sizec);
#     printf("******C*******\n");    
#     inti(c,M,N);
#     print(c,M,N);
#     cudaMalloc(&d_c,sizea);
#     cudaMemcpy(d_c,c,sizec,cudaMemcpyHostToDevice);
#     
#     
#     dim3 dimGrid(1,1);
#     dim3 dimBlock(4, 4);
#     cal<<<dimGrid,dimBlock>>>(d_a,d_b,d_c,M,N,P);
#     cudaMemcpy(c,d_c,sizec,cudaMemcpyDeviceToHost);
#     printf("******C Final*******\n");  
#     print(c,M,N);
#     return 0;
# }



/////////////////////Vector Matrix Mul////////////////////////////
# Commented out IPython magic to ensure Python compatibility.
# %%cu
# #include<stdio.h>
# __global__
# void cal(int *a,int *b,int *c,int N)
# {
#     int col = blockIdx.x*blockDim.x + threadIdx.x;
#     if(col<N)
#     {
#         int sum = 0;
#         for(int i=0;i<N;i++)
#         {
#             sum = sum + a[i]*b[col*N + i]; 
#         }
#         c[col] = sum;
#     }
# }
# int main()
# {
#     int *a, *d_a;
#     const int N = 4;
#     const int sizea = sizeof(int)*N;
#     a = (int *)malloc(sizea);
#     for(int i=0;i<N;i++)
#     {
#         a[i] = rand()%N;
#     }
#     printf("**Vector**\n");
#     for(int i=0;i<N;i++)
#     {
#         printf("%d\t",a[i]);
#     }
#     printf("\n");
#     cudaMalloc(&d_a,sizea);
#     
#     int *b, *d_b;
#     const int sizeb = sizeof(int)*N*N;
#     b = (int *)malloc(sizeb); 
#     for(int i=0;i<N;i++)
#     {
#         for(int j=0;j<N;j++)
#         {
#             if((i*N+j)<N*N)
#                 b[i*N+j] = rand()%(N);
#         }
#     }
#     
#     
#     printf("**Matrix**\n");
#     for(int i=0;i<N;i++)
#     {
#         for(int j=0;j<N;j++)
#         {
#             if((i*N+j)<N*N)
#                 printf("%d\t",b[i*N+j]);
#         }
#         printf("\n");
#     }
#     cudaMalloc(&d_b,sizeb);
#     
#     int *c, *d_c;
#     const int sizec = sizeof(int)*N;
#     c = (int *)malloc(sizec);
#     cudaMalloc(&d_c,sizec);
#     
#     cudaMemcpy(d_a,a,sizea,cudaMemcpyHostToDevice);
#     cudaMemcpy(d_b,b,sizeb,cudaMemcpyHostToDevice);
#     cudaMemcpy(d_c,c,sizec,cudaMemcpyHostToDevice);
#     
#     dim3 dimGrid(1,1);
#     dim3 dimDim(4,4);
#     cal<<<dimGrid,dimDim>>>(d_a,d_b,d_c,N);
#     cudaMemcpy(c,d_c,sizec,cudaMemcpyDeviceToHost);
#     printf("**Final**\n");
#     for(int i=0;i<N;i++)
#     {
#         printf("%d\t",c[i]);
#     }
#     cudaDeviceSynchronize();
#     return 0;
# }
