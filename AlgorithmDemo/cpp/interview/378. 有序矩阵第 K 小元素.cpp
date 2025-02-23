#include <iostream>
#include <cstring>
#include <algorithm>

using namespace std;

const int N = 310;

int n, m, k;
int g[N][N];

int main() {

    // 输入:            输出:
    // 3 3 8            13
    // 1 5 9
    // 10 11 13
    // 12 13 15

    // 输入:            输出:        
    // 1 1 1            -5
    // -5

    cin >> n >> m >> k;
    for (int i = 0; i < n; i ++ )
        for (int j = 0; j < m; j ++ )
            cin >> g[i][j];

    int l = INT_MIN, r = INT_MAX;

    while (l < r) {
        int mid = (long long)l + r >> 1;

        int j = m - 1, cnt = 0;
        for (int i = 0; i < n; i ++ ) {
            while (j >= 0 && g[i][j] > mid) j --;
            cnt += j + 1;
        }

        if (cnt >= k) r = mid;
        else l = mid + 1;
    }

    cout << l << endl;

    return 0;
}