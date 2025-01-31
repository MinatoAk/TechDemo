#include <iostream>
#include <cstring>
#include <algorithm>
#include <unordered_map>

using namespace std;

string s, t;
unordered_map<char, int> hs, ht;

int main() {
    // 输入：s = "ADOBECODEBANC", t = "ABC"     输出："BANC"
    // 输入：s = "a", t = "a"                   输出："a"
    // 输入: s = "a", t = "aa"                  输出: ""

    cin >> s >> t;

    for (auto c : t) ht[c] ++;

    int cnt = 0;
    string res = "";

    for (int l = 0, r = 0; r < s.length(); r ++ ) {
        hs[s[r]] ++;
        if (hs[s[r]] <= ht[s[r]]) cnt ++;

        while (hs[s[l]] > ht[s[l]]) hs[s[l ++ ]] --;

        if (cnt == t.length())
            if (res == "" || res.length() > r - l + 1)
                res = s.substr(l, r - l + 1);
    }

    cout << res << endl;

    return 0;
}