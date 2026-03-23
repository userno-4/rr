#include <stdio.h>

int main()
{
    int n = 4;
    int at[] = {0, 1, 2, 3};
    int bt[] = {10, 5, 8, 6};
    int rt[10], ct[10], tat[10], wt[10];

    int tq;
    printf("Enter Time Quantum: ");
    scanf("%d", &tq);

    for(int i = 0; i < n; i++)
        rt[i] = bt[i];

    int time = 0, done;

    // Store sequence and time
    int seq[100], timeline[100];
    int index = 0;

    timeline[index] = 0; // start time

    while(1)
    {
        done = 1;

        for(int i = 0; i < n; i++)
        {
            if(rt[i] > 0)
            {
                done = 0;

                seq[index] = i; // store process index

                if(rt[i] > tq)
                {
                    time += tq;
                    rt[i] -= tq;
                }
                else
                {
                    time += rt[i];
                    ct[i] = time;
                    rt[i] = 0;
                }

                index++;
                timeline[index] = time;
            }
        }

        if(done == 1)
            break;
    }

    // 🔷 Gantt Chart (Correct Format)
    printf("\nGantt Chart:\n");

    // Print process line
    printf("|");
    for(int i = 0; i < index; i++)
    {
        printf(" P%d |", seq[i] + 1);
    }

    printf("\n");

    // Print time line
    for(int i = 0; i <= index; i++)
    {
        printf("%-5d", timeline[i]);
    }

    printf("\n");

    // Calculate TAT and WT
    for(int i = 0; i < n; i++)
    {
        tat[i] = ct[i] - at[i];
        wt[i] = tat[i] - bt[i];
    }

    // Display results
    printf("\nProcess\tAT\tBT\tCT\tTAT\tWT\n");

    float total_tat = 0, total_wt = 0;

    for(int i = 0; i < n; i++)
    {
        printf("P%d\t%d\t%d\t%d\t%d\t%d\n",i+1, at[i], bt[i], ct[i], tat[i], wt[i]);

        total_tat += tat[i];
        total_wt += wt[i];
    }

    printf("\nAverage Turnaround Time = %.2f", total_tat/n);
    printf("\nAverage Waiting Time = %.2f\n", total_wt/n);

    return 0;
}