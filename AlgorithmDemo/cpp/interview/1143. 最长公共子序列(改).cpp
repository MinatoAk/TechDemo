#include <iostream>
#include <cstring>
#include <algorithm>
#include <vector>

using namespace std;

string a, b;
int n, m;

int main() {
    // 改: 并输出最长公共子序列
    // 输入：text1 = "abcde", text2 = "ace" 
    // 输出：3
    // 输入：text1 = "abc", text2 = "abc"
    // 输出：3
    // 输入：text1 = "abc", text2 = "def"
    // 输出：0

    cin >> a >> b;
    int n = a.length(), m = b.length();
    a = " " + a, b = " " + b;

    vector<vector<int>> dp(n + 1, vector<int>(m + 1));

    for (int i = 1; i <= n; i ++ ) 
        for (int j = 1; j <= m; j ++ ) {
            dp[i][j] = max(dp[i - 1][j], dp[i][j - 1]);
            if (a[i] == b[j]) dp[i][j] = max(dp[i][j], dp[i - 1][j - 1] + 1);
        }

    cout << dp[n][m] << endl;

    // 2) 找到最长上升子序列
    int i = n, j = m;
    string res = "";
    while (i && j) {
        if (a[i] == b[j] && dp[i][j] == dp[i - 1][j - 1] + 1) res += a[i], i --, j --;
        else if (dp[i][j] == dp[i - 1][j]) i --;
        else j --;
    }

    reverse(res.begin(), res.end());

    cout << res << endl;

    return 0;
}