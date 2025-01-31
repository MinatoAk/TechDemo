#include <iostream>
#include <cstring>
#include <algorithm>
#include <queue>

using namespace std;

priority_queue<int> down;
priority_queue<int, vector<int>, greater<int>> up;

double getMid() {
    int sz = down.size() + up.size();
    if (sz & 1) return down.top() * 1.0;
    return (up.top() + down.top()) / 2.0;
}

void addNum(int x) {
    if (down.empty() || down.top() >= x) down.push(x);
    else up.push(x);

    if (down.size() > up.size() + 1) {
        up.push(down.top());
        down.pop();
    }

    if (down.size() < up.size()) {
        down.push(up.top());
        up.pop();
    }
}

int main() {
    string op;
    int x;

    while (cin >> op) {
        if (op == "A") {
            cin >> x;
            addNum(x);
        } else if (op == "F") {
            cout << getMid() << endl;
        }
    }

    return 0;
}