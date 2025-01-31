#include <iostream>
#include <cstring>
#include <algorithm>
#include <vector>

using namespace std;

int n, x, target;
vector<int> nums;
int res = 0x3f3f3f3f;

int main() {
    // 输入：nums = [-1,2,1,-4], target = 1     输出：2
    // 输入：nums = [0,0,0], target = 1         输出：0
    cin >> n >> target;
    for (int i = 0; i < n; i ++ ) cin >> x, nums.push_back(x);

    sort(nums.begin(), nums.end());

    for (int i = 0; i < n; i ++ ) {
        if (i && nums[i] == nums[i - 1]) continue;
        for (int l = i + 1, r = n - 1; l < r; ) {
            int sum = nums[i] + nums[l] + nums[r];

            if (abs(sum - target) < abs(res - target)) res = sum;

            if (sum > target) r --;
            else if (sum < target) l ++;
            else {
                cout << target << endl;
                return 0;
            }
        }
    }

    cout << res << endl;

    return 0;
}