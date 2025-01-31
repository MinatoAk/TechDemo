#include <iostream>
#include <cstring>
#include <algorithm>
#include <vector>
#include <queue>

using namespace std;

const int N = 2010;

int n, m;
vector<int> u[N];

int main() {
    // 输入：numCourses = 2, prerequisites = [[1,0]]        输出：true
    // 输入：numCourses = 2, prerequisites = [[1,0],[0,1]]  输出：false

    cin >> n >> m;
    vector<int> d(n);
    for (int i = 0; i < m; i ++ ) {
        int to, from; cin >> to >> from;
        u[from].push_back(to);
        d[to] ++;
    }

    queue<int> q;
    for (int i = 0; i < n; i ++ )
        if (d[i] == 0) q.push(i);

    int cnt = 0;

    while (!q.empty()) {
        auto t = q.front(); q.pop();
        cnt ++;

        for (int i = 0; i < u[t].size(); i ++ ) {
            int to = u[t][i];
            d[to] --;
            if (d[to] == 0) q.push(to);
        }
    }

    if (cnt == n) cout << "true" << endl;
    else cout << "false" << endl;

    return 0;
}