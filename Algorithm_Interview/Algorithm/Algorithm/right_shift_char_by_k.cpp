#include <iostream>

void reverse(char *str, int begin, int end) {
    for(;begin < end; begin++, end--) {
        char tmp = str[begin];
        str[begin] = str[end];
        str[end] = tmp;
    }
}

void right_shift_char_by_k(char *chr, int len, int k) {
    k = k % len;
    reverse(chr, 0, len - k - 1);
    reverse(chr, len - k, len - 1);
    reverse(chr, 0, len - 1);
}

int main(int argc, char * argv) {
    char str[] = {'a', 'b', 'c', 'd', '1', '2', '3', '4','\0'};

    printf("Before Reverse: %s\n", str);
    right_shift_char_by_k(str, 8, 4);
    printf("After Reverse: %s\n", str);
    
}