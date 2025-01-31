#include <iostream>
#include <cstring>
#include <algorithm>

using namespace std;

/** 
 * 差分: 用于快速在原数组的一段区间内加上同一个值 val
 * 
 * 操作差分数组: b[l] += val, b[r + 1] -= val;
 * 构造差分数组: b[i] += a[i], b[i + 1] -= a[i];
 * 还原原数组: 求差分数组的前缀和
 */

const int N = 1e5 + 10;

int n, m, num;
int b[N];

int main() {
    // 输入样例:            输出样例:
    // 6 3                  3 4 5 3 4 2
    // 1 2 2 1 2 1
    // 1 3 1
    // 3 5 1
    // 1 6 1  

    cin >> n >> m;
    
    // 1) 构造差分数组
    for (int i = 1; i <= n; i ++ ) {
        cin >> num;
        b[i] += num, b[i + 1] -= num;
    }
    
    // 2) 操作差分数组
    int l, r, val;
    while (m -- ) {
        cin >> l >> r >> val;
        b[l] += val, b[r + 1] -= val;
    } 
    
    // 3) 还原原数组
    for (int i = 1; i <= n; i ++ ) {
        b[i] += b[i - 1];
        cout << b[i] << " ";
    }

    return 0;
}