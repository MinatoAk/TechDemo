#include <iostream>
#include <cstring>
#include <algorithm>

using namespace std;

string a, b;
string res;

int main() {
    // 输入：num1 = "11", num2 = "123"  输出："134"
    // 输入：num1 = "456", num2 = "77"  输出："533"
    // 输入：num1 = "0", num2 = "0"     输出："0"

    cin >> a >> b;

    if (a.length() < b.length()) swap(a, b);

    int c = 0;
    int n = a.length(), m = b.length();
    int i = n - 1, j = m - 1;

    while (i >= 0 && j >= 0) {
        int x = a[i --] - '0', y = b[j --] - '0';
        c += x + y;
        res += c % 10 + '0';
        c /= 10;
    }

    while (c || i >= 0) {
        int x = 0;
        if (i >= 0) x = a[i --] - '0';
        c += x;
        res += c % 10 + '0';
        c /= 10;
    }

    reverse(res.begin(), res.end());

    cout << res << endl;

    return 0;
}