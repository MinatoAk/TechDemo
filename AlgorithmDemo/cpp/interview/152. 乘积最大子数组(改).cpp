#include <iostream>
#include <cstring>
#include <algorithm>
#include <vector>

using namespace std;

int n;
vector<int> nums;

int main() {
    // 改: 并且输出该子数组
    // 输入: nums = [2,3,-2,4]  输出: 6 [2,3]
    // 输入: nums = [-2,0,-1]   输出: 0 [0]

    cin >> n;
    for (int i = 0; i < n; i ++ ) {
        int x; cin >> x;
        nums.push_back(x);
    }

    int res = nums[0];
    int minv = res, maxv = res;
    int ed = 0;

    for (int i = 1; i < n; i ++ ) {
        int a = maxv * nums[i], b = minv * nums[i];
        maxv = max(nums[i], max(a, b));
        minv = min(nums[i], min(a, b));
        if (maxv > res) {
            res = maxv;
            ed = i;
        }
    }

    cout << res << endl;
    
    // 输出子数组
    int st = ed, sum = nums[ed];
    while (sum != res) sum *= nums[-- st];
    for (int i = st; i <= ed; i ++ ) cout << nums[i] << " ";

    return 0;
}