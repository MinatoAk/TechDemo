#include <iostream>
#include <cstring>
#include <algorithm>
#include <vector>

using namespace std;

int n, x, target;
vector<int> nums;

int main() {
    // 输入：nums = [4,5,6,7,0,1,2], target = 0     输出：4
    // 输入：nums = [4,5,6,7,0,1,2], target = 3     输出：-1
    // 输入：nums = [1], target = 0                 输出：-1

    cin >> n >> target;
    for (int i = 0; i < n; i ++ ) cin >> x, nums.push_back(x);

    // 1) 找到边界点
    int l = 0, r = n - 1;
    while (l < r) {
        int mid = l + r + 1 >> 1;
        if (nums[mid] >= nums[0]) l = mid;
        else r = mid - 1;
    }

    // 2) 找目标位置
    int st = -1, ed = -1;
    if (target >= nums[0]) st = 0, ed = l;
    else st = l + 1, ed = n - 1;

    while (st < ed) {
        int mid = st + ed >> 1;
        if (nums[mid] >= target) ed = mid;
        else st = mid + 1;
    } 

    if (st > ed || nums[st] != target) cout << -1 << endl;
    else cout << st << endl;

    return 0;
}