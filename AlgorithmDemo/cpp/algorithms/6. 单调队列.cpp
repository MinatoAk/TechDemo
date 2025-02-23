#include <iostream>
#include <cstring>
#include <algorithm>
#include <deque>

using namespace std;

/** 
 * 单调队列: 求出滑动窗口中的最大值 / 最小值 (中位数用对顶堆)
 */

const int N = 1e5 + 10;

int n, k;
int nums[N];
deque<int> q;

int main() {
    // 输入样例:                输出样例:
    // 8 3                      -1 -3 -3 -3 3 3
    // 1 3 -1 -3 5 3 6 7        

    cin >> n >> k;
    for (int i = 0; i < n; i ++ ) cin >> nums[i];

    for (int i = 0; i < n; i ++ ) {
        if (!q.empty() && i - k + 1 > q.front()) q.pop_front();
        while (!q.empty() && nums[q.back()] >= nums[i]) q.pop_back();
        q.push_back(i);
        if (i - k + 1 >= 0) cout << nums[q.front()] << " ";
    }

    return 0;
}