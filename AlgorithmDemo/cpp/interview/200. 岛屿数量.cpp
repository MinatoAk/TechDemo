#include <iostream>
#include <cstring>
#include <algorithm>

using namespace std;

const int N = 310;

int n, m;
int g[N][N];
bool st[N][N];

int dx[4] = {-1, 0, 1, 0};
int dy[4] = {0, 1, 0, -1};

void dfs(int x, int y) {
    st[x][y] = true;

    for (int i = 0; i < 4; i ++ ) {
        int xx = x + dx[i], yy = y + dy[i];
        if (xx >= n || xx < 0 || yy >= m || yy < 0 || st[xx][yy] || g[xx][yy] == 0) continue;
        dfs(xx, yy);
    }
}

int main() {

    cin >> n >> m;

    for (int i = 0; i < n; i ++ )
        for (int j = 0; j < m; j ++ ) 
            cin >> g[i][j];

    int res = 0;
    for (int i = 0; i < n; i ++ )
        for (int j = 0; j < m; j ++ ) 
            if (!st[i][j] && g[i][j] == 1) {
                dfs(i, j);
                res ++;
            }
        
    cout << res << endl;

    return 0;
}