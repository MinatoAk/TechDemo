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

void printList(ListNode* head) {
    while (head) {
        cout << head -> val << " ";
        head = head -> next;
    }
}

ListNode* deleteNode(ListNode* head) {
    if (head == nullptr) return head;

    ListNode* p = head;
    while (p) {
        auto q = p;
        if (p -> next && p -> next -> val == p -> val) {
            while (q -> next && q -> next -> val == q -> val) q = q -> next;
            p -> next = q -> next;
        }
        p = p -> next;
    }

    return head;
}

int main() {
    // 输入：head = [1,1,2]     输出：[1,2]
    // 输入：head = [1,1,2,3,3] 输出：[1,2,3]

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