#include <iostream>
#include <cstring>
#include <algorithm>
#include <vector>

using namespace std;

int n, x;
vector<int> nums;
vector<vector<int>> res;

int main() {
    // 输入：nums = [-1,0,1,2,-1,-4]    输出：[[-1,-1,2],[-1,0,1]]
    // 输入：nums = [0,1,1]             输出：[]
    // 输入：nums = [0,0,0]             输出：[[0,0,0]]
    cin >> n;
    for (int i = 0; i < n; i ++ ) cin >> x, nums.push_back(x);

    sort(nums.begin(), nums.end());

    for (int i = 0; i < n; i ++ ) {
        if (i && nums[i] == nums[i - 1]) continue;
        for (int l = i + 1, r = n - 1; l < r; ) {
            int sum = nums[i] + nums[l] + nums[r];
            if (sum < 0) l ++;
            else if (sum > 0) r --;
            else  {
                res.push_back({nums[i], nums[l], nums[r]});
                l ++, r --;
            }
            
            while (l != i + 1 && l < r && nums[l] == nums[l - 1]) l ++;
            while (r != n - 1 && l < r && nums[r] == nums[r + 1]) r --;
        }
    }

    for (auto ans : res) {
        for (auto x : ans) {
            cout << x << " ";
        }
        cout << endl;
    }

    return 0;
}