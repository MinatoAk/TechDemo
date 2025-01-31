#include <iostream>
#include <cstring>
#include <algorithm>
#include <vector>

using namespace std;

string s;
int resL = 1, resLen = 1;

int main() {
    // 输入：s = "babad"    输出："bab"
    // 输入：s = "cbbd"     输出："bb"
    cin >> s;
    int n = s.length();
    s = " " + s;
    vector<vector<bool>> dp(n + 1, vector<bool>(n + 1, false));

    for (int i = 1; i <= n; i ++ ) dp[i][i] = true;

    for (int len = 2; len <= n; len ++ )
        for (int l = 1; l + len - 1 <= n; l ++ ) {
            int r = l + len - 1;
            if (s[l] == s[r]) {
                if (len == 2) dp[l][r] = true;
                else dp[l][r] = dp[l + 1][r - 1];
            }

            if (dp[l][r]) resL = l, resLen = len;
        }

    cout << s.substr(resL, resLen) << endl;

    return 0;
}