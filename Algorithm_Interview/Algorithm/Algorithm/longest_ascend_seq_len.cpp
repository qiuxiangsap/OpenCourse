/*****************************************************************************************/
/* 题目: 写一个时间复杂度尽可能低的程序， 求一个数组（N个元素）中最长递增子序列的长度
/* 出处: 编程之美 p194
/* 作者: Zhongcun Wang
/* 日期: 2014.7.19
/*******************************************************************************************/
#include <iostream>
#include <climits>
#include <cassert>
#include <algorithm>
using namespace std;

int binary_search(int *arr, int low, int high, int elem) {
    while(low <= high) {
        int mid = (low + high) / 2;
        if (arr[mid] > elem) {
            high = mid - 1;
        } else if( arr[mid] < elem ) {
            low = mid + 1;
        } else {
            return mid;
        }
    }
    return low;
}

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
    /** max = max_element(longest, longest + len) */

    return max;
}

/** O(N^2) */
int longest_ascend_seq_len_s2(int *arr, int len) {
    assert(len > 0);
    int * longest = new int[len];
    int * maxlvl = new int[len];

    int nMaxlvl = 1;
    maxlvl[0] = *min_element(arr, arr + len) - 1;
    maxlvl[1] = arr[0];

    for (int i = 0; i < len; i++) {
        longest[i] = 1;
        int j;
        for (j = nMaxlvl; j >= 0; j--) {
            if (arr[i] > maxlvl[j]) {
                longest[i] = j + 1;
                break;
            }
        }
        if (longest[i] > nMaxlvl) {
            nMaxlvl = longest[i];
            maxlvl[nMaxlvl] = arr[i];
        } else if (arr[i] > maxlvl[j] && arr[i] < maxlvl[j + 1]) {
            maxlvl[j + 1] = arr[i];
        }
    }
    return nMaxlvl;
}

/** O(nlog(n))*/
int longest_ascend_seq_len_s3(int *arr, int len) {
    int *maxlvl = new int[len];
    int *longest = new int[len];

    maxlvl[0] = *min_element(arr, arr + len)  - 1;
    maxlvl[1] = arr[0];
    int nmaxlvl = 1;

    for (int i = 0; i < len; i++) {
        longest[i] = 1;
        int j;
//      j = lower_bound(maxlvl, maxlvl + nmaxlvl + 1, arr[i]) - maxlvl ;
        j = binary_search(maxlvl, 0 , nmaxlvl, arr[i]);
        longest[i] = j;

        if (nmaxlvl < longest[i]) {
            nmaxlvl = longest[i];
            maxlvl[nmaxlvl] = arr[i];
        } else if ( arr[i] < maxlvl[j]) {
            maxlvl[j] = arr[i];
        }

    }
    return nmaxlvl;
}

int main() {
    int arr[] = {1, -1, 2, -3, 4, -5, 6, - 7};
    int target = 4;

    // test first version of implementation 
    int predict_s1 = longest_ascend_seq_len_s1(arr, sizeof(arr) / sizeof(arr[0]));
    assert(target == predict_s1);

    int predict_s2 = longest_ascend_seq_len_s2(arr, sizeof(arr) / sizeof(arr[0]));
    cout << "Predict_S2:" << predict_s2 << endl;

    int predict_s3 = longest_ascend_seq_len_s3(arr, sizeof(arr) / sizeof(arr[0]));
    cout << "Predict_S3:" << predict_s3 << endl;
      
}