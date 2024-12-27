#include <iostream>
#include <cstring>
#include <algorithm>
#include <vector>

using namespace std;

int n, m;
vector<int> nums1, nums2;

int findKth(int i, int j, int k) {
    if (nums1.size() - i > nums2.size() - j) return (j, i, k);

    if (nums1.size() == i) return nums2[j + k - 1];
    if (k == 1) return min(nums1[i], nums2[j]);

    int si = min((int)nums1.size(), i + k / 2), sj = j + k / 2;
    if (nums1[si - 1] > nums2[sj - 1]) return findKth(i, sj, k - k / 2);
    return findKth(si, j, k - (si - i));
}

double getMid() {
    int sz = nums1.size() + nums2.size();

    if (sz & 1) return 1.0 * findKth(0, 0, sz / 2 + 1);

    int l = findKth(0, 0, sz / 2);
    int r = findKth(0, 0, sz / 2 + 1);

    return (l + r) / 2.0;
}

int main() {
    // 输入：nums1 = [1,3], nums2 = [2]     输出：2.00000
    // 输入：nums1 = [1,2], nums2 = [3,4]   输出：2.50000

    cin >> n >> m;
    int x;
    for (int i = 0; i < n; i ++ ) cin >> x, nums1.push_back(x);
    for (int j = 0; j < m; j ++ ) cin >> x, nums2.push_back(x);

    cout << getMid() << endl;

    return 0;
}