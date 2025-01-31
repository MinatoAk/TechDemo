#include <iostream>
#include <cstring>
#include <algorithm>

using namespace std;

/** 
 * 二维前缀和: 用于快速求出子矩阵的和
 * 
 * 得到前缀和数组 O(nm): s[i][j] = s[i - 1][j] + s[i][j - 1] - s[i - 1][j - 1] + a[i][j] 
 * 求子矩阵和 O(1): sum = s[x2][y2] - s[x1 - 1][y2] - s[x2][y1 - 1] + s[x1 - 1][y1 - 1]
 * 
 * 注意点: 下标从 s[1][1] 开始算, s[0][0] = 0
 */

const int N = 1010;

int n, m, t;
int g[N][N];

int query(int x1, int y1, int x2, int y2) {
    return g[x2][y2] - g[x2][y1 - 1] - g[x1 - 1][y2] + g[x1 - 1][y1 - 1];
}

int main() {
    // 输入用例:            输出用例:
    // 3 4 3                17
    // 1 7 2 4              27
    // 3 6 2 8              21
    // 2 1 2 3
    // 1 1 2 2
    // 2 1 3 4
    // 1 3 3 4
    cin >> n >> m >> t;

    for (int i = 1; i <= n; i ++ )
        for (int j = 1; j <= m; j ++ ) {
            cin >> g[i][j];
            g[i][j] += g[i - 1][j] + g[i][j - 1] - g[i - 1][j - 1];
        }

    int x1, x2, y1, y2;
    while (t -- ) {
        cin >> x1 >> y1 >> x2 >> y2;
        cout << query(x1, y1, x2, y2) << endl;
    }

    return 0;
}