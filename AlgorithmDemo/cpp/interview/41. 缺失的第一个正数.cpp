#include <iostream>
#include <cstring>
#include <algorithm>
#include <vector>

using namespace std;

int n;
vector<int> nums;

int main() {
    // 输入：nums = [1,2,0]         输出：3
    // 输入：nums = [3,4,-1,1]      输出：2
    // 输入：nums = [7,8,9,11,12]   输出：1


    cin >> n;
    for (int i = 0; i < n; i ++ ) {
        int x; cin >> x;
        nums.push_back(x);
    }

    for (auto &x : nums) x --;

    for (int i = 0; i < n; i ++ ) 
        while (nums[i] >= 0 && nums[i] < n && nums[i] != i && nums[nums[i]] != nums[i])
            swap(nums[i], nums[nums[i]]);
    
    for (int i = 0; i < n; i ++ )
        if (nums[i] != i) {
            cout << i + 1 << endl;
            return 0;
        }

    cout << n + 1 << endl;

    return 0;
}