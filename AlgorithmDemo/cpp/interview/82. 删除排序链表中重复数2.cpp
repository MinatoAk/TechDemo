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

int n;
vector<int> nums;

ListNode* buildList() {
    ListNode* dummy = new ListNode(-1), *cur = dummy;

    for (int i = 0; i < n; i ++ ) {
        ListNode* node = new ListNode(nums[i]);
        cur = cur -> next = node;
    }

    return dummy -> next;
}

ListNode* deleteNode(ListNode* head) {
    ListNode* dummy = new ListNode(-1, head), *pre = dummy, *p = head;

    while (p) {
        if (p -> next && p -> next -> val == p -> val) {
            while (p -> next && p -> next -> val == p -> val) p = p -> next;
            pre -> next = p -> next;
            p = p -> next;
        } else {
            pre = p, p = p -> next;
        }
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
    // 输入: head = [1,2,3,3,4,4,5] 输出: [1,2,5]
    // 输入: head = [1,1,1,2,3]     输出: [2,3]
    cin >> n;
    for (int i = 0; i < n; i ++ ) {
        int x; cin >> x;
        nums.push_back(x);
    }

    ListNode* head = buildList();

    head = deleteNode(head);

    printList(head);

    return 0;
}