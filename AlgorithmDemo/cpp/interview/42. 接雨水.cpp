#include <iostream>
#include <cstring>
#include <algorithm>
#include <vector>
#include <stack>

using namespace std;

int n, res;
vector<int> h;
stack<int> stk;

int main() {
    // 输入：height = [0,1,0,2,1,0,1,3,2,1,2,1]     输出：6
    // 输入：height = [4,2,0,3,2,5]                 输出：9
    cin >> n;
    for (int i = 0; i < n; i ++ ) {
        int x; cin >> x;
        h.push_back(x);
    }

    for (int i = 0; i < n; i ++ ) {
        while (!stk.empty() && h[i] >= h[stk.top()]) {
            int idx = stk.top(); stk.pop();
            if (!stk.empty()) {
                int hh = max(0, min(h[i], h[stk.top()]) - h[idx]);
                int ww = i - stk.top() - 1;
                res += hh * ww;
            }
        }
        stk.push(i);
    }

    cout << res << endl;

    return 0;
}