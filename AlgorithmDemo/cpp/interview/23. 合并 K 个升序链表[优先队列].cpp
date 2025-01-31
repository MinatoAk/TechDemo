#include <iostream>
#include <cstring>
#include <algorithm>
#include <queue>
#include <vector>

using namespace std;

struct ListNode {
    int val;
    ListNode* next;
    ListNode(int val): val(val), next(nullptr) {}
};

int n, m, x;
vector<ListNode*> lists;

ListNode* buildList(vector<int> nums) {
    ListNode* dummy = new ListNode(-1), *cur = dummy;

    for (int i = 0; i < nums.size(); i ++ ) {
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

struct cmp {
    bool operator() (ListNode* a, ListNode* b) {
        return a -> val > b -> val;
    }
};

int main() {
    // 输入：lists = [[1,4,5],[1,3,4],[2,6]]    
    // 输出：[1,1,2,3,4,4,5,6]

    cin >> n;
    for (int i = 0; i < n; i ++ ) {
        vector<int> tmp;
        cin >> m;
        for (int i = 0; i < m; i ++ ) cin >> x, tmp.push_back(x);
        ListNode* head = buildList(tmp);
        lists.push_back(head);
    }

    priority_queue<ListNode*, vector<ListNode*>, cmp> q;

    for (auto list: lists) 
        if (list) q.push(list);

    ListNode* dummy = new ListNode(-1), *cur = dummy;

    while (!q.empty()) {
        auto t = q.top(); q.pop();
        cur = cur -> next = t;
        if (t -> next) q.push(t -> next);
    }

    auto head = dummy -> next;
    printList(head);

    return 0;
}