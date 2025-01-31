/**
 * 题目内容: 
 * 有一个长度为 n 的数组，如果一个数组有一个唯一的最大值，那么这个数组就是一个好数组，求这个数组有多少连续子数组是好数组。
 * 连续子数组是指在原数组中，连续的选择一段元素组成的新数组。
 * 
 * 输入描述: 
 * 第一行输入一个整数 n (1e5) 表示数组中的元素数量。
 * 第二行输入 n 个整数表 (1e9) 示数组元素。
 * 
 * 输出描述: 在一行上输出一个整数，表示有多少连续子数组是好数组。
 * 
 * 输入样例: 
 * 5
 * 1 2 5 4 5
 * 
 * 输出样例: 
 * 12
 */
#include <iostream>
#include <cstring>
#include <algorithm>
#include <stack>

using namespace std;

const int N = 1e5 + 10;

int n;
int a[N], l[N], r[N];

int main() {
    cin >> n;
    for (int i = 0; i < n; i ++ ) cin >> a[i];

    stack<int> stk;
    for (int i = 0; i < n; i ++ ) {
        while (!stk.empty() && a[stk.top()] < a[i]) stk.pop();
        if (!stk.empty()) l[i] = stk.top();
        else l[i] = -1;
        stk.push(i);
    }

    while (!stk.empty()) stk.pop();
    for (int i = n - 1; i >= 0; i -- ) {
        while (!stk.empty() && a[stk.top()] < a[i]) stk.pop();
        if (!stk.empty()) r[i] = stk.top();
        else r[i] = n;
        stk.push(i);
    }

    int res = 0;
    for (int i = 0; i < n; i ++ ) res += (i - l[i]) * (r[i] - i);

    cout << res << endl;

    return 0;
}

/**
 * 思路:
 * 
 * 使用单调栈维护每个元素左侧和右侧首个大于等于当前元素的元素下标;
 * 之后以每个元素 a[i] 为最大值的连续子区间的个数为 (i - l[i]) * (r[i] - i)
 */