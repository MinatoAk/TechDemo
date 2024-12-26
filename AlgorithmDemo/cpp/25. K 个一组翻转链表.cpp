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

int n, k;
vector<int> nums;

ListNode* buildList() {
    ListNode* dummy = new ListNode(-1), *cur = dummy;

    for (int i = 0; i < n; i ++ ) {
        ListNode * node = new ListNode(nums[i]);
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

ListNode* reverseKList(ListNode* head) {
    ListNode* dummy = new ListNode(-1, head), *p = dummy;

    while (p) {
        // 1) 确认是否满 K 个
        auto q = p;
        for (int i = 0; i < k; i ++ )
            if (q) q = q -> next;
        if (q == nullptr) break;

        // 2) 翻转组内节点
        auto a = p -> next, b = a -> next;
        while (a != q) {
            auto c = b -> next;
            b -> next = a;
            a = b, b = c;
        }

        // 3) 调整指针
        auto c = p -> next;
        p -> next = a;
        c -> next = b;
        p = c;
    }

    return dummy -> next;
}

int main() {
    // 输入: head = [1,2,3,4,5], k = 2  输出: [2,1,4,3,5]
    // 输入: head = [1,2,3,4,5], k = 3  输出: [3,2,1,4,5]

    cin >> n >> k;
    for (int i = 0; i < n; i ++ ) {
        int x; cin >> x;
        nums.push_back(x);
    }

    ListNode* head = buildList();

    head = reverseKList(head);

    printList(head);

    return 0;
}