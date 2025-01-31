#include <iostream>
#include <algorithm>
#include <cstring>
#include <unordered_map>

using namespace std;

const int N = 2e4 + 10;

int n, k;
int nums[N];
unordered_map<int, int> cnt;

int main() {
    // 输入：nums = [1,1,1], k = 2  输出：2
    // 输入：nums = [1,2,3], k = 3  输出：2

    cin >> n >> k;
    for (int i = 0; i < n; i ++ ) cin >> nums[i];

    cnt[0] = 1;
    int sum = 0, res = 0;

    for (int i = 0; i < n; i ++ ) {
        sum += nums[i];
        res += cnt[sum - k];
        cnt[sum] ++;
    }

    cout << res << endl;

    return 0;
}