#include <iostream>
#include <cstring>
#include <algorithm>
#include <vector>
#include <unordered_map>

using namespace std;

struct TreeNode {
    int val;
    TreeNode* left, *right;
    TreeNode(int val): val(val), left(nullptr), right(nullptr) {}
};

int n, x;
vector<int> pre, mid;
unordered_map<int, int> p;

TreeNode* buildTree(int pl, int pr, int ml, int mr) {
    if (pl > pr) return nullptr;
    TreeNode* node = new TreeNode(pre[pl]);
    int k = p[pre[pl]], len = k - ml;
    node -> left = buildTree(pl + 1, pl + len, ml, k - 1);
    node -> right = buildTree(pl + len + 1, pr, k + 1, mr);
    return node;
}

void postOrderPrint(TreeNode* root) {
    if (root == nullptr) return;
    postOrderPrint(root -> left);
    postOrderPrint(root -> right);
    cout << root -> val << " ";
}

int main() {
    // 前中构造二叉树，并后序输出
    // 输入: preorder = [3,9,20,15,7], inorder = [9,3,15,20,7]
    // 输出: [9, 15, 7, 20, 3]
    cin >> n;
    for (int i = 0; i < n; i ++ ) cin >> x, pre.push_back(x);
    for (int i = 0; i < n; i ++ ) cin >> x, mid.push_back(x), p[x] = i;

    TreeNode* root = buildTree(0, n - 1, 0, n - 1);
    
    postOrderPrint(root);

    return 0;
}