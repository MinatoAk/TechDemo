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

int n, l, r;
vector<int> nums;

ListNode* buildList() {
    ListNode* dummy = new ListNode(-1), *cur = dummy;

    for (int i = 0; i < n; i ++ ) {
        ListNode* node = new ListNode(nums[i]);
        cur = cur -> next = node;
    }

    return dummy -> next;
}

ListNode* reverseList(ListNode* head) {
    ListNode* dummy = new ListNode(-1, head), *pre = nullptr, *p = dummy, *q = dummy;

    for (int i = 0; i < r; i ++ ) {
        if (i < l) pre = p, p = p -> next;
        q = q -> next;
    }

    auto a = p, b = p -> next;
    while (a != q) {
        auto c = b -> next;
        b -> next = a;
        a = b, b = c;
    }

    auto c = pre -> next;
    pre -> next = a;
    c -> next = b;

    return dummy -> next;
}

void printList(ListNode* head) {
    while (head) {
        cout << head -> val << " ";
        head = head -> next;
    }
}

int main() {
    // 输入：head = [1,2,3,4,5], left = 2, right = 4    输出：[1,4,3,2,5]
    // 输入：head = [5], left = 1, right = 1            输出：[5]

    cin >> n >> l >> r;
    for (int i = 0; i < n; i ++ ) {
        int x; cin >> x;
        nums.push_back(x);
    }
    
    ListNode* head = buildList();

    head = reverseList(head);

    printList(head);

    return 0;
}