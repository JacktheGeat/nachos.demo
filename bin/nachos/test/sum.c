  #include "stdio.h"

#define BUFFERSIZE = 16
  // i dont know C, so im sorry this can't be more interesting, 
    int main() {
        char aChar;
        char bChar;
        printf("insert value for 'a' (0-9): ");
        readline(aChar, BUFFERSIZE);
        int a = atoi(aChar);
        printf("insert value for 'b' (0-9): ");
        readline(bChar, BUFFERSIZE);
        int b = atoi(bChar);
        
        printf("\na: %d\nb: %d\n", a, b);

        int sum_without_carry = a ^ b; 
        int carry = (a & b) << 1;

        int full_sum = a;
        int temp_b = b;
        while (temp_b != 0) {
            int current_sum = full_sum ^ temp_b;
            int current_carry = (full_sum & temp_b) << 1;
            full_sum = current_sum;
            temp_b = current_carry;
        }
        printf("Final sum: %d\n", full_sum);

        return 0;
    }
