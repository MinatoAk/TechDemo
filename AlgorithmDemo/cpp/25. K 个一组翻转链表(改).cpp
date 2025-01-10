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

int n, k, x;
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

ListNode* reverseList(ListNode* head) {
    if (head == nullptr || head -> next == nullptr) return head;
    auto tail = reverseList(head -> next);

    head -> next -> next = head;
    head -> next = nullptr;

    return tail;
}

ListNode* reverseKLists(ListNode* head) {
    ListNode* dummy = new ListNode(-1, head), *p = dummy;

    while (p) {
        // 1) 看是否满 k 个元素
        auto q = p;
        for (int i = 0; i < k; i ++ )
            if (q) q = q -> next;
        
        if (q == nullptr) {
            p -> next = reverseList(p -> next);
            break;
        }

        // 2) 翻转组内节点
        auto a = p -> next, b = a -> next;
        while (a != q) {
            auto c = b -> next;
            b -> next = a;
            a = b, b = c;
        }

        // 3) 调整节点
        auto c = p -> next;
        p -> next = a;
        c -> next = b;
        p = c;
    }

    return dummy -> next;
}

int main() {
    // 改: 最后不足 K 个也要进行翻转
    // 输入: [1 -> 2 -> 3 -> 4 -> 5 -> 6 -> 7 -> 8], k = 3
    // 输出: [3 -> 2 -> 1 -> 6 -> 5 -> 4 -> 8 -> 7]

    cin >> n >> k;
    for (int i = 0; i < n; i ++ ) cin >> x, nums.push_back(x);

    ListNode* head = buildList();

    head = reverseKLists(head);

    printList(head);

    return 0;
}