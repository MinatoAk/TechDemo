#include <iostream>
#include <cstring>
#include <vector>
#include <algorithm>

using namespace std;

int n, k;
vector<int> nums;

string res, sk;

string itos(int x) {
    string s = "";

    while (x) {
        s += (x % 10) + '0';
        x /= 10;
    }

    reverse(s.begin(), s.end());
    return s;
}

bool cmp(int a, int b) {
    return a > b;
}

bool dfs(int u, string ans, bool isEqual) {
    if (u == sk.length()) {
        if (ans < sk) {
            res = ans;
            return true;
        }
        return false;
    }

    for (int i = 0; i < n; i ++ ) {
        char c = nums[i] + '0';
        if (isEqual && c > sk[u]) continue;
        ans += c;
        if (dfs(u + 1, ans, c == sk[u])) return true;
        ans.pop_back();
    }

    return false;
}

int main() {

    cin >> n >> k;
    for (int i = 0; i < n; i ++ ) {
        int x; cin >> x;
        nums.push_back(x);
    }

    sk = itos(k);
    sort(nums.begin(), nums.end(), cmp);
    
    bool success = dfs(0, "", true);

    if (!success)
        for (int i = 0; i < sk.length() - 1; i ++ )
            res += nums[0] + '0';

    cout << res << endl;

    return 0;
}