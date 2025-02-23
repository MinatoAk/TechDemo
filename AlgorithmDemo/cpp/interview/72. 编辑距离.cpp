#include <iostream>
#include <cstring>
#include <algorithm>
#include <vector>

using namespace std;

string s1, s2;
int n, m;

int main() {
    // 输入：s1 = "horse", s2 = "ros"
    // 输出：3
    // 输入：word1 = "intention", word2 = "execution"
    // 输出：5

    cin >> s1 >> s2;
    n = s1.length(), m = s2.length();
    s1 = " " + s1, s2 = " " + s2;

    vector<vector<int>> dp(n + 1, vector<int>(m + 1, 0x3f3f3f3f));

    for (int i = 0; i <= n; i ++ ) dp[i][0] = i;
    for (int i = 0; i <= m; i ++ ) dp[0][i] = i;

    for (int i = 1; i <= n; i ++ )
        for (int j = 1; j <= m; j ++ ) {
            dp[i][j] = min(dp[i - 1][j], dp[i][j - 1]) + 1;
            if (s1[i] == s2[j]) dp[i][j] = min(dp[i][j], dp[i - 1][j - 1]);
            else dp[i][j] = min(dp[i][j], dp[i - 1][j - 1] + 1);
        }

    cout << dp[n][m] << endl;

    return 0;
}