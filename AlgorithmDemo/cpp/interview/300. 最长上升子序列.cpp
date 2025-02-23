#include <iostream>
#include <cstring>
#include <algorithm>
#include <vector>

using namespace std;

int n;
vector<int> nums, pos;

int main() {
    // 输入：nums = [10,9,2,5,3,7,101,18]   输出：4
    // 输入：nums = [0,1,0,3,2,3]           输出：4
    // 输入：nums = [7,7,7,7,7,7,7]         输出：1

    cin >> n;
    for (int i = 0; i < n; i ++ ) {
        int x; cin >> x;
        nums.push_back(x);
    }

    for (auto x : nums) {
        if (pos.empty() || x > pos.back()) pos.push_back(x);
        else {
            if (pos[0] >= x) pos[0] = x;
            else {
                int l = 0, r = pos.size() - 1;
                while (l < r) {
                    int mid = l + r >> 1;
                    if (pos[mid] >= x) r = mid;
                    else l = mid + 1;
                }
                pos[l] = x;
            }
        }
    }

    cout << pos.size() << endl;
    for (auto x : pos) cout << x << " ";

    return 0;
}