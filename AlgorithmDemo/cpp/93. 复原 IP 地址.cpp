#include <iostream>
#include <algorithm>
#include <cstring>
#include <vector>

using namespace std;

string s;
vector<string> res;

int toInt(string s) {
    int ans = 0;

    for (int i = 0; i < s.length(); i ++ ) {
        ans = ans * 10 + s[i] - '0';
    }

    return ans;
}

bool isValid(string t) {
    int x = toInt(t);
    if (x == 0 && t.length() > 1) return false;
    if (x != 0 && t[0] == '0') return false;
    return x <= 255 && x >= 0;
}

void dfs(int u, int st, string ans) {
    if (u == 4) {
        res.push_back(ans);
        return;
    }

    if (u == 3) {
        int len = s.length() - st;
        if (len > 3 || len <= 0) return;
        string t = s.substr(st, len);
        if (isValid(t)) dfs(u + 1, st + len, ans + t);

    } else {
        for (int len = 1; len <= 3 && st + len - 1 < s.length(); len ++ ) {
            string t = s.substr(st, len);
            if (isValid(t)) dfs(u + 1, st + len, ans + t + ".");
        }
    }
}

int main() {
    // 输入：s = "25525511135"  输出：["255.255.11.135","255.255.111.35"]
    // 输入：s = "0000"         输出：["0.0.0.0"]
    // 输入：s = "101023"       输出：["1.0.10.23","1.0.102.3","10.1.0.23","10.10.2.3","101.0.2.3"]
    cin >> s;

    dfs(0, 0, "");

    for (auto str : res) cout << str << endl;

    return 0;
}