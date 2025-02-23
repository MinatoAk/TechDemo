#include <iostream>
#include <cstring>
#include <algorithm>
#include <stack>

using namespace std;

/** 
 * 单调栈: 用于求出每个元素左侧/右侧首个比它大/小的元素
 */

const int N = 1e5 + 10;

int n;
int nums[N];

int main() {
    // 输入样例:            输出样例:
    // 5                    -1 3 -1 2 2
    // 3 4 2 7 5
    cin >> n;
    for (int i = 0; i < n; i ++ ) cin >> nums[i];

    stack<int> stk;
    for (int i = 0; i < n; i ++ ) {
        while (!stk.empty() && nums[stk.top()] >= nums[i]) stk.pop();
        if (stk.empty()) cout << -1 << " ";
        else cout << nums[stk.top()] << " ";
        stk.push(i);
    }

    return 0;
}