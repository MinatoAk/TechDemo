#include <iostream>
#include <cstring>
#include <algorithm>
#include <vector>

using namespace std;

vector<int> ans;
vector<vector<int>> res;
bool st[20];
int n;

void dfs(int u) {
    if (u == n) {
        res.push_back(ans);
        return;
    }

    for (int i = 1; i <= n; i ++ ) {
        if (st[i]) continue;
        ans.push_back(i);
        st[i] = true;
        dfs(u + 1);
        st[i] = false;
        ans.pop_back();
    }
}

int main() {

    cin >> n;

    dfs(0);

    for (auto ans : res) {
        for (auto x : ans) cout << x << " ";
        cout << endl;
    }

    return 0;
}