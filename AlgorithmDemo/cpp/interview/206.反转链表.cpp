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

// 解法(1): 递归实现
ListNode* reverseList(ListNode* head) {
    if (head == nullptr || head -> next == nullptr) return head;
    auto tail = reverseList(head -> next);
    
    head -> next -> next = head;
    head -> next = nullptr;

    return tail;
}

// 解法(2): 迭代实现
ListNode* reverseList2(ListNode* head) {
    ListNode* pre = nullptr, *p = head;
    while (p) {
        auto c = p -> next;
        p -> next = pre;
        pre = p, p = c;
    }
    return pre;
}

int main() {
    // 输入: head = [1,2,3,4,5] 输出: [5,4,3,2,1]
    // 输入: head = [1,2]       输出: [2,1]
    // 输入: head = []          输出: head = []

    cin >> n;
    if (n == 0) return 0;

    for (int i = 0; i < n; i ++ ) {
        int x; cin >> x;
        nums.push_back(x);
    }

    ListNode* head = buildList();

    head = reverseList2(head);

    printList(head);

    return 0;
}