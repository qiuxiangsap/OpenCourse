/*****************************************************************************************/
/* 题目: 写一个时间复杂度尽可能低的程序， 求一个数组（N个元素）中最长递增子序列的长度
/* 出处: 编程之美 p194
/* 作者: Zhongcun Wang
/* 日期: 2014.7.19
/*******************************************************************************************/
#include <iostream>
#include <climits>
#include <cassert>
using namespace std;

/** O(n^2) */
int longest_ascend_seq_len_s1(int *arr, int len) {
    int *longest = new int[len];

    for (int i = 0; i < len; i++) {
        longest[i] = 1;
        for (int j = 0; j < i; j++) {
            if ( (arr[j] < arr[i]) && (longest[i] < longest[j] + 1) ) {
                longest[i] = longest[j] + 1;
            }
        }
    }

    int max = INT_MIN;
    for (int i = 0; i < len; i++) {
        if (longest[i] > max) {
            max = longest[i];
        }
    }

    return max;
}

int main() {
    int arr[] = {1, -1, 2, -3, 4, -5, 6, - 7};
    int target = 4;
    int predict = longest_ascend_seq_len_s1(arr, sizeof(arr) / sizeof(arr[0]));
    cout << "Predict: " << predict << " " << "target:" << target << endl;
    assert(target == predict);
      
}