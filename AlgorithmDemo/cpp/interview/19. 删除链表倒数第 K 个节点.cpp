#include <iostream>
#include <cstring>
#include <algorithm>
#include <vector>

using namespace std;

struct ListNode {
    int val;
    ListNode* next;
    ListNode(int val): val(val), next(nullptr) {}
    ListNode(int val, ListNode* next): val(val), next(next) {}
};

int n, x, k;
vector<int> nums;

ListNode* buildList() {
    ListNode* dummy = new ListNode(-1), *cur = dummy;

    for (int i = 0; i < n; i ++ ) {
        ListNode* node = new ListNode(nums[i]);
        cur = cur -> next = node;
    }

    return dummy -> next;
}

void printList(ListNode* head) {
    while (head) {
        cout << head -> val << " ";
        head = head -> next;
    }
}

int main() {
    // 输入:                    输出:
    // 1 2 3 4 5                1 2 3 5
    // 2
    // 1                        []
    // 1
    // 1 2                      1
    // 1

    cin >> n >> k;
    for (int i = 0; i < n; i ++ ) cin >> x, nums.push_back(x);

    ListNode* head = buildList();

    ListNode* dummy = new ListNode(-1, head), *fast = dummy, *slow = dummy;

    for (int i = 0; i < k; i ++ ) fast = fast -> next;

    while (fast -> next) {
        fast = fast -> next;
        slow = slow -> next;
    }

    slow -> next = slow -> next -> next;

    printList(dummy -> next);

    return 0;
}