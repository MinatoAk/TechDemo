#include <iostream>
#include <cstring>
#include <algorithm>
#include <vector>
#include <set>

using namespace std;

int n, k;
vector<int> nums;
vector<double> res;
multiset<int> down, up;

double getMid() {
    if (k & 1) return *down.rbegin() * 1.0;
    return (*up.begin() + *down.rbegin()) / 2.0;
}

void insertNum(int x) {
    if (x <= *down.rbegin()) down.insert(x);
    else up.insert(x);
}

void removeNum(int x) {
    if (x <= *down.rbegin()) down.erase(down.find(x));
    else up.erase(up.find(x));
}

void balance() {
    if (down.size() > up.size() + 1) {
        up.insert(*down.rbegin());
        down.erase(down.find(*down.rbegin()));
    }

    if (down.size() < up.size()) {
        down.insert(*up.begin());
        up.erase(up.find(*up.begin()));
    }
}

int main() {
    // 输入: nums = [1,3,-1,-3,5,3,6,7], k = 3
    // 输出: [1,-1,-1,3,5,6]
    cin >> n >> k;
    for (int i = 0; i < n; i ++ ) {
        int x; cin >> x;
        nums.push_back(x);
    }

    for (int i = 0; i < k; i ++ ) down.insert(nums[i]);
    for (int i = 0; i < k / 2; i ++ ) {
        up.insert(*down.rbegin());
        down.erase(down.find(*down.rbegin()));
    }

    res.push_back(getMid());

    for (int i = k; i < n; i ++ ) {
        int x = nums[i], y = nums[i - k];
        insertNum(x);
        removeNum(y);
        balance();
        res.push_back(getMid());
    }

    for (auto x : res) cout << x << " ";

    return 0;
}