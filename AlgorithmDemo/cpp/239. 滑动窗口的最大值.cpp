#include <iostream>
#include <cstring>
#include <algorithm>
#include <deque>
#include <vector>

using namespace std;

int n, k;
vector<int> nums;
vector<int> res;

int main() {
    // 输入：nums = [1,3,-1,-3,5,3,6,7], k = 3
    // 输出：[3,3,5,5,6,7]

    cin >> n >> k;
    for (int i = 0; i < n; i ++ ) {
        int x; cin >> x;
        nums.push_back(x);
    }

    deque<int> q;
    for (int i = 0; i < n; i ++ ) {
        if (!q.empty() && i - k + 1 > q.front()) q.pop_front();
        while (!q.empty() && nums[q.back()] <= nums[i]) q.pop_back();
        q.push_back(i);
        if (i - k + 1 >= 0) res.push_back(nums[q.front()]);
    }

    for (auto x : res) cout << x << " ";

    return 0;
}