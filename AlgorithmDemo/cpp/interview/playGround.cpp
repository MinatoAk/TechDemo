#include <iostream>
#include <algorithm>
#include <cstring>
#include <vector>

using namespace std;

string s;

int main() {
    cin >> s;

    int n = s.length();
    vector<int> dp(n);

    int res = 0;

    for (int i = 1; i < n; i ++ ) {
        int matchIdx = i - dp[i - 1] - 1;
        if (s[i] == ')' && matchIdx >= 0 &&  s[matchIdx] == '(') {
            dp[i] = 2 + dp[i - 1];
            if (matchIdx - 1 >= 0) dp[i] += dp[matchIdx - 1];
            res = max(res, dp[i]);
        }
    }

    cout << res << endl;

    return 0;
}