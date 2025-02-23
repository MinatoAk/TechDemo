#include <iostream>
#include <cstring>
#include <algorithm>
#include <vector>

using namespace std;

int n, m;
vector<int> a, b;

int findKthMin(vector<int> nums1, int i, vector<int> nums2, int j, int k) {
    // 1) 先保证 nums1 是长度小的数组
    if (nums1.size() - i > nums2.size() - j) return findKthMin(nums2, j, nums1, i, k);

    // 2) 递归终止条件: nums1 为空或 k == 1
    if (nums1.size() == i) return nums2[j + k - 1];
    if (k == 1) return min(nums1[i], nums2[j]);

    // 3) 类似于归并
    int si = min(i + k / 2, (int)nums1.size()), sj = j + k / 2;
    if (nums1[si - 1] > nums2[sj - 1]) return findKthMin(nums1, i, nums2, sj, k - k / 2);
    return findKthMin(nums1, si, nums2, j, k - (si - i));
}

double getRes() {
    int sz = n + m;

    if (sz & 1) return findKthMin(a, 0, b, 0, sz / 2 + 1) * 1.0;

    int l = findKthMin(a, 0, b, 0, sz / 2);
    int r = findKthMin(a, 0, b, 0, sz / 2 + 1);

    return (l + r) / 2.0;
}

int main() {
    // 输入：nums1 = [1,3], nums2 = [2]     输出：2.00000
    // 输入：nums1 = [1,2], nums2 = [3,4]   输出：2.50000

    int x;
    cin >> n >> m;
    for (int i = 0; i < n; i ++ ) cin >> x, a.push_back(x);
    for (int i = 0; i < m; i ++ ) cin >> x, b.push_back(x);


    double res = getRes();

    cout << res << endl;

    return 0;
}