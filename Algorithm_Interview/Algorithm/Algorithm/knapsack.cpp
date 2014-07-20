#include<iostream>
#include <algorithm>
using namespace std;

int zero_one_knapsack(int *weight, int *value, int len, int total) {

    int * f = new int[total + 1];
    for (int i = 0; i <= total; i++) {
        f[i] = 0;
    }

    for (int i = 0; i < len; i++) {
        for (int j = total; j >= weight[i]; j--) {
            f[j] = max(f[j], f[j - weight[i]] + value[i]);
        }
    }
    return f[total];
}

int complete_knapsack(int *weight, int * value, int len, int total) {
    int *f = new int[total + 1];
    for (int i = 0; i <= total; i++ ) {
        f[i] = 0;
    }

    for (int i = 0; i < len; i++) {
        for (int j = weight[i]; j <= total; j++) {
            f[j] = max(f[j], (f[j - weight[i]] + value[i]));
        }
    }

    return f[total];
}
int main(int argc, char *argv[]) {
    int weight[] = {2, 2, 6, 5, 4};
    int value[] = {6, 3, 5, 4, 6};
    int total = 10;

    int target = zero_one_knapsack(weight, value, sizeof(weight) / sizeof(weight[0]), total);
    cout << "Target is : " << target << endl;

    int target_complete = complete_knapsack(weight, value, sizeof(weight) / sizeof(weight[0]), total);
    cout << "Target value for complete knapsack problem is : " << target_complete << endl;
}