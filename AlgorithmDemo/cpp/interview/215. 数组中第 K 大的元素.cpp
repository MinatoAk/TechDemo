#include <iostream>
#include <cstring>
#include <algorithm>
#include <vector>
#include <queue>

using namespace std;

int n, k;
vector<int> nums;

// 解法(1): 维护大小为 k 的小顶堆 O(nlogk)
int findKth() {
    priority_queue<int, vector<int>, greater<int>> q;

    for (int i = 0; i < n; i ++ ) {
        if (q.size() == k) {
            int t = q.top(); q.pop();
            int toInsert = max(t, nums[i]);
            q.push(toInsert);
        } else q.push(nums[i]);
    }

    return q.top();
}

// 解法(2): 快速选择 O(n)
int quickSelect(int l, int r, int kk) {
    if (l >= r) return nums[l];

    int i = l - 1, j = r + 1;
    int mid = l + r >> 1, x = nums[mid];

    while (i < j) {
        do i ++; while (nums[i] < x);
        do j --; while (nums[j] > x);
        if (i < j) swap(nums[i], nums[j]);
    }

    int rLen = r - j;
    if (rLen >= kk) return quickSelect(j + 1, r, kk);
    return quickSelect(l, j, k - rLen);
}

int main() {
    // {{ 无序数组找中位数 }} 其实也是同理
    // 输入: [3,2,1,5,6,4]  k = 2           输出: 5
    // 输入: [3,2,3,1,2,4,5,5,6]  k = 4     输出: 4
    
    cin >> n >> k;
    for (int i = 0; i < n; i ++ ) {
        int x; cin >> x;
        nums.push_back(x);
    }

    int res = quickSelect(0, n, k);

    cout << res << endl;

    return 0;
}