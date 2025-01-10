#include <iostream>
#include <cstring>
#include <algorithm>
#include <unordered_map>

using namespace std;

string s;
int k, res;
unordered_map<char, int> mp;

int main() {
    // 改: 最多允许 K 个重复字符
    // 输入: s = "abcabcbb" k = 1   输出: 3
    // 输入: s = "abcabcbb" k = 3   输出: 7
    // 输入: s = "bbbbb"    k = 2   输出: 2
    // 输入: s = "pwwkew"   k = 2   输出: 5

    cin >> s >> k;
    
    for (int l = 0, r = 0; r < s.length(); r ++ ) {
        mp[s[r]] ++;
        while (mp[s[r]] > k) mp[s[l ++ ]] --;
        res = max(res, r - l + 1);
    }
    
    cout << res << endl;

    return 0;
}