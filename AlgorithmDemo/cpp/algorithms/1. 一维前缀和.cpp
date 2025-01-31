#include <iostream>
#include <cstring>
#include <algorithm>

using namespace std;

/** 
 * 前缀和: 用于快速求出数组一段区间内的和
 * 
 * 得到前缀和数组 O(n): s[i] = s[i - 1] + a[i]
 * 求区间和 O(1): sum = s[r] - s[l - 1]
 * 
 * 注意点: 下标从 s[1] 开始算, s[0] = 0
 */

const int N = 1e5 + 10;

int n, m;
int nums[N];

int main() {
    // 输入样例:            输出样例:
    // 5 3                  3
    // 2 1 3 6 4            6
    // 1 2                  10
    // 1 3
    // 2 4    

    cin >> n >> m;
    for (int i = 1; i <= n; i ++ ) cin >> nums[i], nums[i] += nums[i - 1];

    int l, r;
    while (m -- ) {
        cin >> l >> r;
        cout << nums[r] - nums[l - 1] << endl;
    }

    return 0;
}