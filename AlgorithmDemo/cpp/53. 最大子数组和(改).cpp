#include <iostream>
#include <cstring>
#include <algorithm>
#include <vector>

using namespace std;

int n, x;
vector<int> nums;

int main() {
    // 改: 输出子数组
    // 输入：nums = [-2,1,-3,4,-1,2,1,-5,4]     输出：6
    // 输入：nums = [5,4,-1,7,8]                输出：23
    // 输入：nums = [1]                         输出：1

    cin >> n;
    for (int i = 0; i < n; i ++ ) cin >> x, nums.push_back(x);

    int sum = -0x3f3f3f3f;
    int resSt = 0, resEd = 0, res = -0x3f3f3f3f;
    int curSt = 0;

    for (int i = 0; i < n; i ++ ) {
        if (sum <= 0) sum = nums[i], curSt = i;
        else sum += nums[i];

        if (sum > res) {
            res = sum;
            resSt = curSt, resEd = i;
        }
    }

    cout << res << endl;
    for (int i = resSt; i <= resEd; i ++ ) cout << nums[i] << " ";

    return 0;
}