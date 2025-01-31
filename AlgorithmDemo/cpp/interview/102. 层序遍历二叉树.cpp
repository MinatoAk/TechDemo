#include <iostream>
#include <cstring>
#include <algorithm>
#include <vector>
#include <queue>

using namespace std;

struct TreeNode {
    int val;
    TreeNode* left, *right;
    TreeNode(int val): val(val), left(nullptr), right(nullptr) {}
};

int n, x;
vector<int> nums;
vector<vector<int>> res;

TreeNode* buildTree(int idx) {
    if (idx >= n || nums[idx] == -1) return nullptr;

    TreeNode* root = new TreeNode(nums[idx]);
    root -> left = buildTree(idx * 2 + 1);
    root -> right = buildTree(idx * 2 + 2);

    return root;
}

// 解法1: BFS
void bfs(TreeNode* root) {
    queue<TreeNode*> q;

    q.push(root);
    while (!q.empty()) {
        int sz = q.size();
        vector<int> ans;

        for (int i = 0; i < sz; i ++ ) {
            auto t = q.front(); q.pop();
            ans.push_back(t -> val);
            if (t -> left) q.push(t -> left);
            if (t -> right) q.push(t -> right);
        }

        res.push_back(ans);
    }
}

// 解法2: DFS
void dfs(TreeNode* root, int u) {
    if (root == nullptr) return;

    if (res.size() < u + 1) res.push_back({root -> val});
    else res[u].push_back(root -> val);

    dfs(root -> left, u + 1);
    dfs(root -> right, u + 1);
}

int main() {
    // 输入：root = [3,9,20,null,null,15,7]     输出：[[3],[9,20],[15,7]]
    // 输入：root = [1]                         输出：[[1]]
    cin >> n;
    for (int i = 0; i < n; i ++ ) cin >> x, nums.push_back(x);

    TreeNode* root = buildTree(0);

    // bfs(root);
    dfs(root, 0);

    for (auto ans : res) {
        for (auto x : ans) cout << x << " ";
        cout << endl;
    }

    return 0;
}