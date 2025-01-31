#include <iostream>
#include <cstring>
#include <algorithm>

using namespace std;

/** 
 * 最大公约数: 扩展欧几里得算法，大数模小数，或者调库 __gcd(a, b)
 * 最小公倍数: lcm(a, b) = (a * b) / gcd(a, b)
 */

int n, a, b;

int gcd(int a, int b) {
    return b ? gcd(b, a % b) : a;
}

int lcm(int a, int b) {
    return (a * b) / gcd(a, b);
}

int main() {
    // 输入样例:            输出样例:
    // 2                    3 6
    // 3 6                  2 12
    // 4 6 
    cin >> n;

    while (n -- ) {
        cin >> a >> b;
        cout << gcd(a, b) << " " << lcm(a, b) << endl;
    }

    return 0;
}