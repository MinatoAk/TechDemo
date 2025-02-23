#include <iostream>
#include <cstring>
#include <algorithm>
#include <unordered_map>
#include <stack>

using namespace std;

string s;
unordered_map<char, int> mp;

void init() {
    mp['('] = 1, mp[')'] = -1;
    mp['['] = 2, mp[']'] = -2;
    mp['{'] = 3, mp['}'] = -3;
}

int main() {
    // 改: 要求必须按照 {} [] () 的优先级顺序关闭
    // 输入:                输出:
    // ()                   true
    // )()                  false
    // (]                   false
    // ([])                 false
    // [()]                 true
    // (){}[]               true

    init();
    cin >> s;

    stack<int> stk;

    for (auto c : s) {
        int x = mp[c];

        if (x < 0) {
            if (stk.empty() || stk.top() + x != 0) {
                cout << "false" << endl;
                return 0;
            }
            stk.pop();
        } else {
            if (!stk.empty() && stk.top() < x) {
                cout << "false" << endl;
                return 0;
            }
            stk.push(x);
        }
    }

    if (stk.empty()) cout << "true" << endl;
    else cout << "false" << endl;

    return 0;
}