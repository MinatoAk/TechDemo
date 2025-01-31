#include <iostream>
#include <cstring>
#include <algorithm>
#include <vector>

using namespace std;

int n, x;
vector<int> nums;

void quickSort(int l, int r) {
    if (l >= r) return;

    int i = l - 1, j = r + 1;
    int mid = l + r >> 1, x = nums[mid];

    while (i < j) {
        do i ++; while (nums[i] < x);
        do j --; while (nums[j] > x);
        if (i < j) swap(nums[i], nums[j]);
    }

    quickSort(l, j), quickSort(j + 1, r);
}

int main() {
    // 输入：nums = [5,2,3,1]       输出：[1,2,3,5]
    // 输入：nums = [5,1,1,2,0,0]   输出：[0,0,1,1,2,5]
    cin >> n;
    for (int i = 0; i < n; i ++ ) cin >> x, nums.push_back(x);

    quickSort(0, n - 1);

    for (auto x : nums) cout << x << " ";

    return 0;
}