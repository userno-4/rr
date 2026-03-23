#include <stdio.h>

int main() {
    int n = 4;
    int at[] = {2, 3, 10, 12}; 
    int bt[] = {5, 4, 2, 4}; 
    int rt[10], ct[10], tat[10], wt[10];
    int tq;

    printf("Enter Time Quantum: ");
    if (scanf("%d", &tq) != 1) return 1;

    for(int i = 0; i < n; i++) rt[i] = bt[i];

    int time = 0, completed = 0;
    int seq[100], timeline[100];
    int index = 0;
    timeline[index] = 0; 

    while(completed < n) {
        int executed_this_cycle = 0;

        for(int i = 0; i < n; i++) {
            if(at[i] <= time && rt[i] > 0) {
                executed_this_cycle = 1;
                seq[index] = i;

                if(rt[i] > tq) {
                    time += tq;
                    rt[i] -= tq;
                } else {
                    time += rt[i];
                    ct[i] = time;
                    rt[i] = 0;
                    completed++;
                }
                index++;
                timeline[index] = time;
            }
        }

        if(executed_this_cycle == 0) {
            seq[index] = -1; 
            time++;
            index++;
            timeline[index] = time;
        }
    }

    printf("\nGantt Chart:\n|");
    for(int i = 0; i < index; i++) {
        if(seq[i] == -1) 
            printf(" IDLE |");
        else 
            printf("  P%d  |", seq[i] + 1);
    }
    printf("\n");
    for(int i = 0; i <= index; i++) {
        printf("%-7d", timeline[i]); 
    }
    printf("\n");

    float total_tat = 0, total_wt = 0;
    printf("\nProcess\tAT\tBT\tCT\tTAT\tWT\n");
    for(int i = 0; i < n; i++) {
        tat[i] = ct[i] - at[i];
        wt[i] = tat[i] - bt[i];
        printf("P%d\t%d\t%d\t%d\t%d\t%d\n", i + 1, at[i], bt[i], ct[i], tat[i], wt[i]);
        total_tat += tat[i];
        total_wt += wt[i];
    }

    printf("\nAverage Turnaround Time = %.2f", total_tat / n);
    printf("\nAverage Waiting Time = %.2f\n", total_wt / n);

    return 0;
}

// ============================================================================================

#include <stdio.h>
#include <stdbool.h>

#define MAX 100

int main(){
        int n, m;
        int alloc[MAX][MAX], max[MAX][MAX], need[MAX][MAX];
        int avail[MAX];

        printf("Enter number of processes: ");
        scanf("%d", &n);

        printf("Enter number of resource types: ");
        scanf("%d", &m);

        printf("\nEnter Allocation Matrix:\n");
        for(int i = 0; i < n; i++){
                for(int j = 0; j < m; j++){
                        scanf("%d", &alloc[i][j]);
                }
        }

        printf("\nEnter Maximum Matrix:\n");
        for(int i = 0; i < n; i++){
                for(int j = 0; j < m; j++){
                        scanf("%d", &max[i][j]);
                }
        }

        printf("\nEnter Available Resources:\n");
        for(int i = 0; i < m; i++){
                scanf("%d", &avail[i]);
        }

        for(int i = 0; i < n; i++){
                for(int j = 0; j < m; j++){
                        need[i][j] = max[i][j] - alloc[i][j];
                }
        }

        bool finish[MAX] = {false};
        int safeSeq[MAX];
        int work[MAX];

        for(int i = 0; i < m; i++){
                work[i] = avail[i];
        }

        int count = 0;

        while(count < n){
                bool found = false;

                for(int i = 0; i < n; i++){
                        if(!finish[i]){
                                bool canExecute = true;

                                for(int j = 0; j < m; j++){
                                        if(need[i][j] > work[j]){
                                                canExecute = false;
                                                break;
                                        }
                                }

                                if(canExecute){
                                        for(int j = 0; j < m; j++){
                                                work[j] += alloc[i][j];
                                        }

                                        safeSeq[count++] = i;
                                        finish[i] = true;
                                        found = true;
                                }
                        }
                }

                if(!found){
                        printf("\nSystem is NOT in safe state.\n");
                        return 0;
                }
        }

        printf("\nSystem is in SAFE state.\nSafe Sequence: ");
        for(int i = 0; i < n; i++){
                printf("P%d ", safeSeq[i]);
        }

        // Resource Request

        int process;
        int request[MAX];

        printf("\nEnter process number making request: ");
        scanf("%d", &process);

        printf("Enter request vector:\n");
        for(int i = 0; i < m; i++){
                scanf("%d", &request[i]);
        }

        for(int i = 0; i < m; i++){
                if(request[i] > need[process][i]){
                        printf("\nError: Request exceeds Need.\n");
                        return 0;
                }
        }

        for(int i = 0; i < m; i++){
                if(request[i] > avail[i]){
                        printf("\nRequest cannot be granted immediately (not enough resources).\n");
                        return 0;
                }
        }

        for(int i = 0; i < m; i++){
                avail[i] -= request[i];
                alloc[process][i] += request[i];
                need[process][i] -= request[i];
        }

        for(int i = 0; i < n; i++){
                finish[i] = false;
        }

        for(int i = 0; i < m; i++){
                work[i] = avail[i];
        }

        count = 0;

        while(count < n){
                bool found = false;

                for(int i = 0; i < n; i++){
                        if(!finish[i]){
                                bool canExecute = true;

                                for(int j = 0; j < m; j++){
                                        if(need[i][j] > work[j]){
                                                canExecute = false;
                                                break;
                                        }
                                }

                                if(canExecute){
                                        for(int j = 0; j < m; j++){
                                                work[j] += alloc[i][j];
                                        }

                                        safeSeq[count++] = i;
                                        finish[i] = true;
                                        found = true;
                                }
                        }
                }

                if(!found){
                        printf("\nRequest Denied: System would be Unsafe.\n");
                        return 0;
                }
        }

        printf("\nRequest Granted.\nNew Safe Sequence: ");
        for(int i = 0; i < n; i++){
                printf("P%d ",safeSeq[i]);
        }

        return 0;
}