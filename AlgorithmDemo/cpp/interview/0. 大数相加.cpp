#include <iostream>
#include <cstring>
#include <algorithm>

using namespace std;

string x, y;

string add(string a, string b) {
    string res = "";
    if (a.length() < b.length()) swap(a, b);

    int i = a.length() - 1, j = b.length() - 1, c = 0;
    while (i >= 0 && j >= 0) {
        int t1 = a[i -- ] - '0', t2 = b[j -- ] - '0';
        c += t1 + t2;
        res += (c % 10) + '0';
        c /= 10;
    }

    while (c || i >= 0) {
        int t1 = 0;
        if (i >= 0) t1 = a[i --] - '0';
        c += t1;
        res += (c % 10) + '0';
        c /= 10;
    }

    reverse(res.begin(), res.end());

    return res;
}

bool cmp(string a, string b) {
    if (a.length() != b.length()) return a.length() > b.length();
    return a >= b;
}

string sub(string a, string b) {
    string res = "";

    int i = a.length() - 1, j = b.length() - 1, t = 0;

    while (i >= 0 && j >= 0) {
        int t1 = a[i -- ] - '0', t2 = b[j -- ] - '0';
        t = t1 - t2 - t;
        res += (t + 10) % 10 + '0';
        if (t < 0) t = 1;
        else t = 0;
    }

    while (i >= 0) {
        int t1 = a[i -- ] - '0';
        t = t1 - t;
        res += (t + 10) % 10 + '0';
        if (t < 0) t = 1;
        else t = 0;
    }

    while (res.length() > 1 && res.back() == '0') res.pop_back();

    reverse(res.begin(), res.end());

    return res;
}

int main() {
    // 输入:            输出:
    // 11 1234          1245
    // 1234 0           1234
    // -11 -1234        -1245
    // 1234 -11         1223
    // 1234 -1235       -1
    // -1234 11         -1223


    cin >> x >> y;

    if (x[0] != '-' && y[0] != '-') cout << add(x, y) << endl;
    else if (x[0] == '-' && y[0] == '-') {
        string xx = x.substr(1, x.length() - 1), yy = y.substr(1, y.length() - 1);
        cout << "-" << add(xx, yy) << endl;

    } else if (x[0] == '-' && y[0] != '-') {
        string xx = x.substr(1, x.length() - 1);
        if (cmp(xx, y)) cout << "-" << sub(xx, y) << endl;
        else cout << sub(y, xx) << endl;

    } else if (x[0] != '-' && y[0] == '-') {
        string yy = y.substr(1, y.length() - 1);
        if (cmp(x, yy)) cout << sub(x, yy) << endl;
        else cout << "-" << sub(yy, x) << endl;

    }

    return 0;
}