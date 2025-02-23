#include <iostream>
#include <cstring>
#include <algorithm>
#include <vector>

using namespace std;

struct ListNode {
    int val;
    ListNode * next;
    ListNode(int val): val(val), next(nullptr) {}
    ListNode(int val, ListNode* next): val(val), next(next) {}
};

int n, x, pos;
vector<int> nums;

ListNode* buildList(int pos) {
    ListNode* dummy = new ListNode(-1), *cur = dummy;
    ListNode* cycleEnd = nullptr;

    for (int i = 0; i < n; i ++ ) {
        ListNode* node = new ListNode(nums[i]);
        if (i == pos) cycleEnd = node;
        cur = cur -> next = node;
    }

    cur -> next = cycleEnd;
    return dummy -> next;
}

int main() {
    // 输入:                输出:
    // 3 2 0 -4             true    
    // 1
    // 1 2                  true
    // 0
    // 1                   false
    // -1

    cin >> n >> pos;
    if (n == 0) {
        cout << "false" << endl;
        return 0;
    }
    for (int i = 0; i < n; i ++ ) cin >> x, nums.push_back(x);

    ListNode* head = buildList(pos);

    ListNode* fast = head, *slow = head;

    do {
        if (fast -> next == nullptr || fast -> next -> next == nullptr) {
            cout << "false" << endl;
            return 0;
        }
        fast = fast -> next -> next;
        slow = slow -> next;
    } while (slow != fast);

    cout << "true" << endl;

    return 0;
}