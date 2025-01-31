#include <iostream>
#include <cstring>
#include <algorithm>
#include <vector>

using namespace std;

int n;
vector<int> nums;

int main() {
    // 输入: nums = [2,3,-2,4]  输出: 6
    // 输入: nums = [-2,0,-1]   输出: 0

    cin >> n;
    for (int i = 0; i < n; i ++ ) {
        int x; cin >> x;
        nums.push_back(x);
    }

    int res = nums[0];
    int minv = res, maxv = res;

    for (int i = 1; i < n; i ++ ) {
        int a = maxv * nums[i], b = minv * nums[i];
        maxv = max(nums[i], max(a, b));
        minv = min(nums[i], min(a, b));
        res = max(res, maxv);
    }

    cout << res << endl;

    return 0;
}