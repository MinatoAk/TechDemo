#include <iostream>
#include <cstring>
#include <algorithm>
#include <vector>

using namespace std;

string s;

int main() {
    // 输入：s = "(()"      输出：2
    // 输入：s = ")()())"   输出：4
    // 输入：s = ""         输出：0

    cin >> s;

    int n = s.length();
    vector<int> dp(n, 0);
    int res = 0;

    for (int i = 1; i < n; i ++ ) {
        int matchIdx = i - dp[i - 1] - 1;
        if (s[i] == ')' && matchIdx >= 0 && s[matchIdx] == '(') {
            dp[i] = 2 + dp[i - 1];
            dp[i] += matchIdx - 1 >= 0 ? dp[matchIdx - 1] : 0;
            res = max(res, dp[i]);
        }
    }

    cout << res << endl;

    return 0;
}