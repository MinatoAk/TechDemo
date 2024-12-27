#include <iostream>
#include <cstring>
#include <algorithm>
#include <vector>

using namespace std;

struct TreeNode {
    int val;
    TreeNode* left;
    TreeNode* right;
    TreeNode(int val): val(val), left(nullptr), right(nullptr) {}
};

int n, res = -0x3f3f3f3f;
vector<int> nums;

TreeNode* buildTree(int idx) {
    if (idx >= n || nums[idx] == -1) return nullptr;

    TreeNode* root = new TreeNode(nums[idx]);
    root -> left = buildTree(idx * 2 + 1);
    root -> right = buildTree(idx * 2 + 2);

    return root;
}

int dfs(TreeNode* root) {
    if (root == nullptr) return 0;

    int l = max(0, dfs(root -> left));
    int r = max(0, dfs(root -> right));
    res = max(res, l + r + root -> val);

    return max(l, r) + root -> val;
}

int main() {
    // 输入：root = [1,2,3]                     输出：6
    // 输入：root = [-10,9,20,null,null,15,7]   输出：42
    cin >> n;
    for (int i = 0; i < n; i ++ ) {
        int x; cin >> x;
        nums.push_back(x);
    }

    TreeNode* root = buildTree(0);

    dfs(root);

    cout << res << endl;

    return 0;
}