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