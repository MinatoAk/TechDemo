#include <iostream>
#include <cstring>
#include <algorithm>
#include <unordered_map>

using namespace std;

string s;

int main() {
    // 输入: s = "abcabcbb" 输出: 3
    // 输入: s = "bbbbb"    输出: 1
    // 输入: s = "pwwkew"   输出: 3
    cin >> s;

    unordered_map<char, int> mp;
    int res = 0;

    for (int l = 0, r = 0; r < s.length(); r ++ ) {
        mp[s[r]] ++;
        while (mp[s[r]] > 1) mp[s[l ++ ]] --;
        res = max(res, r - l + 1);
    }

    cout << res << endl;

    return 0;
}