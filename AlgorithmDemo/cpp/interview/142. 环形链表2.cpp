#include <iostream>
#include <cstring>
#include <algorithm>
#include <vector>

using namespace std;

struct ListNode {
    int val;
    ListNode* next;
    ListNode(int val): val(val), next(nullptr) {}
};

int n, pos, x;
vector<int> nums;

ListNode* buildList(int pos) {
    ListNode* dummy = new ListNode(-1), *cur = dummy;
    ListNode* cycleEnd = nullptr;

    for (int i = 0; i < n; i ++ ) {
        ListNode * node = new ListNode(nums[i]);
        if (pos == i) cycleEnd = node;
        cur = cur -> next = node;
    }

    cur -> next = cycleEnd;

    return dummy -> next;
}

int main() {
    // 输入:                输出:
    // 3 2 0 -4             2
    // 1
    // 1 2                  1
    // 0
    // 1                    null
    // -1

    cin >> n >> pos;
    for (int i = 0; i < n; i ++ ) cin >> x, nums.push_back(x);

    ListNode* head = buildList(pos);

    ListNode* fast = head, *slow = head;
    do {
        if (fast -> next == nullptr || fast -> next -> next == nullptr) {
            cout << "null" << endl;
            return 0;
        }
        fast = fast -> next -> next;
        slow = slow -> next;
    } while (fast != slow);

    slow = head;
    while (slow != fast) {
        slow = slow -> next;
        fast = fast -> next;
    }

    cout << slow -> val << endl;

    return 0;
}