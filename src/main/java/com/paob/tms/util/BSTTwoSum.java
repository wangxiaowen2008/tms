package com.paob.tms.util;

import java.util.HashSet;
import java.util.Set;

/**
 * 二叉搜索树两数之和工具类
 * 给定一个二叉搜索树 root 和一个目标结果 k，如果二叉搜索树中存在两个元素且它们的和等于给定的目标结果，则返回 true。
 * 
 * @author paob
 * @since 2024-01-01
 */
public class BSTTwoSum {

    /**
     * 二叉搜索树节点定义
     */
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        
        TreeNode() {}
        
        TreeNode(int val) {
            this.val = val;
        }
        
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    /**
     * 方法1：使用HashSet进行两数之和查找
     * 时间复杂度：O(n)，其中n是树中节点的数量
     * 空间复杂度：O(n)，用于存储HashSet
     * 
     * @param root 二叉搜索树根节点
     * @param k 目标和
     * @return 如果存在两个数的和等于k则返回true，否则返回false
     */
    public static boolean findTarget(TreeNode root, int k) {
        Set<Integer> set = new HashSet<>();
        return findTargetHelper(root, k, set);
    }
    
    /**
     * 递归辅助方法，使用中序遍历查找两数之和
     * 
     * @param root 当前节点
     * @param k 目标和
     * @param set 存储已访问节点的值的集合
     * @return 是否找到两数之和
     */
    private static boolean findTargetHelper(TreeNode root, int k, Set<Integer> set) {
        if (root == null) {
            return false;
        }
        
        // 如果当前节点的值在集合中存在，说明找到了两数之和
        if (set.contains(k - root.val)) {
            return true;
        }
        
        // 将当前节点的值添加到集合中
        set.add(root.val);
        
        // 递归遍历左子树和右子树
        return findTargetHelper(root.left, k, set) || findTargetHelper(root.right, k, set);
    }

    /**
     * 方法2：使用中序遍历将BST转换为有序数组，然后使用双指针查找
     * 时间复杂度：O(n)
     * 空间复杂度：O(n)
     * 
     * @param root 二叉搜索树根节点
     * @param k 目标和
     * @return 如果存在两个数的和等于k则返回true，否则返回false
     */
    public static boolean findTargetWithInorder(TreeNode root, int k) {
        java.util.List<Integer> list = new java.util.ArrayList<>();
        inorderTraversal(root, list);
        
        // 使用双指针查找两数之和
        int left = 0;
        int right = list.size() - 1;
        
        while (left < right) {
            int sum = list.get(left) + list.get(right);
            if (sum == k) {
                return true;
            } else if (sum < k) {
                left++;
            } else {
                right--;
            }
        }
        
        return false;
    }
    
    /**
     * 中序遍历，将BST转换为有序数组
     * 
     * @param root 当前节点
     * @param list 存储有序值的列表
     */
    private static void inorderTraversal(TreeNode root, java.util.List<Integer> list) {
        if (root == null) {
            return;
        }
        
        inorderTraversal(root.left, list);
        list.add(root.val);
        inorderTraversal(root.right, list);
    }

    /**
     * 方法3：使用BST的特性，结合双指针思想
     * 时间复杂度：O(n)
     * 空间复杂度：O(h)，其中h是树的高度
     * 
     * @param root 二叉搜索树根节点
     * @param k 目标和
     * @return 如果存在两个数的和等于k则返回true，否则返回false
     */
    public static boolean findTargetWithBSTProperty(TreeNode root, int k) {
        if (root == null) {
            return false;
        }
        
        // 创建两个栈来模拟中序遍历
        java.util.Stack<TreeNode> leftStack = new java.util.Stack<>();
        java.util.Stack<TreeNode> rightStack = new java.util.Stack<>();
        
        // 初始化左栈（从小到大）
        TreeNode current = root;
        while (current != null) {
            leftStack.push(current);
            current = current.left;
        }
        
        // 初始化右栈（从大到小）
        current = root;
        while (current != null) {
            rightStack.push(current);
            current = current.right;
        }
        
        // 双指针查找
        while (!leftStack.isEmpty() && !rightStack.isEmpty() && 
               leftStack.peek() != rightStack.peek()) {
            
            int sum = leftStack.peek().val + rightStack.peek().val;
            
            if (sum == k) {
                return true;
            } else if (sum < k) {
                // 左指针向右移动
                current = leftStack.pop().right;
                while (current != null) {
                    leftStack.push(current);
                    current = current.left;
                }
            } else {
                // 右指针向左移动
                current = rightStack.pop().left;
                while (current != null) {
                    rightStack.push(current);
                    current = current.right;
                }
            }
        }
        
        return false;
    }

    /**
     * 测试方法
     */
    public static void main(String[] args) {
        // 创建测试用例：BST [5,3,6,2,4,null,7]
        //       5
        //      / \
        //     3   6
        //    / \   \
        //   2   4   7
        
        TreeNode root = new TreeNode(5);
        root.left = new TreeNode(3);
        root.right = new TreeNode(6);
        root.left.left = new TreeNode(2);
        root.left.right = new TreeNode(4);
        root.right.right = new TreeNode(7);
        
        int target = 9;
        
        System.out.println("测试BST: [5,3,6,2,4,null,7]");
        System.out.println("目标和: " + target);
        
        // 测试方法1
        boolean result1 = findTarget(root, target);
        System.out.println("方法1结果: " + result1);
        
        // 测试方法2
        boolean result2 = findTargetWithInorder(root, target);
        System.out.println("方法2结果: " + result2);
        
        // 测试方法3
        boolean result3 = findTargetWithBSTProperty(root, target);
        System.out.println("方法3结果: " + result3);
        
        // 测试其他目标值
        System.out.println("\n测试其他目标值:");
        System.out.println("目标和 28: " + findTarget(root, 28)); // 应该返回false
        System.out.println("目标和 6: " + findTarget(root, 6));  // 应该返回true (2+4)
        System.out.println("目标和 12: " + findTarget(root, 12)); // 应该返回true (5+7)
    }
}
