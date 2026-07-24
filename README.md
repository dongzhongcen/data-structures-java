# Java 数据结构笔记

这个仓库用于整理 Java 数据结构课程笔记、代码示例和复习资料。

GitHub 上想实现截图里那种“点击展开 / Click to expand”的效果，可以在 `README.md` 里使用 HTML 的 `<details>` 和 `<summary>` 标签。

## 目录

<details>
<summary><strong>数据结构基础部分</strong>（点击展开 Click to expand）</summary>

- [第 1 节 数据结构基础准备知识](docs/01-数据结构基础准备知识/README.md)
- [第 2 节 JDK17 与顺序表](docs/02-JDK17与顺序表/README.md)
- [第 3 节 试卷讲解与顺序表](docs/03-试卷讲解与顺序表/README.md)
- [第 4 节 链表](docs/04-链表/README.md)
- [第 5 节 链表](docs/05-链表进阶/README.md)
- [第 6 节 链表完结与栈队列](docs/06-链表完结与栈队列/README.md)
- [第 7 节 栈队列与二叉树](docs/07-栈队列与二叉树/README.md)
- [第 8 节 二叉树](docs/08-二叉树基础/README.md)
- [第 9 节 二叉树](docs/09-二叉树进阶/README.md)
- [第 10 节 三叉树](docs/10-三叉树/README.md)
- [第 11 节 二叉树完结与堆](docs/11-二叉树完结与堆/README.md)
- [第 12 节 堆与排序](docs/12-堆与排序/README.md)
- [第 13 节 排序](docs/13-排序一/README.md)
- [第 14 节 排序](docs/14-排序二/README.md)
- [第 15 节 Map 与 Set 1](docs/15-Map与Set一/README.md)
- [第 16 节 Map 与 Set 2](docs/16-Map与Set二/README.md)
- [第 17 节 反射、枚举、Lambda 等](docs/17-反射枚举Lambda等/README.md)
- [第 18 节 试卷讲解与复习](docs/18-试卷讲解与复习/README.md)

</details>

<details>
<summary><strong>高级数据结构部分</strong>（点击展开 Click to expand）</summary>

- [第 1 节 AVL 树](docs/advanced/01-AVL树/README.md)
- [第 2 节 AVL 与红黑树](docs/advanced/02-AVL与红黑树/README.md)
- [第 3 节 红黑树与位图布隆过滤器](docs/advanced/03-红黑树与位图布隆过滤器/README.md)
- [第 4 节 布隆过滤器与海量数据并查集](docs/advanced/04-布隆过滤器与海量数据并查集/README.md)
- [第 5 节 并查集与 LRU Cache](docs/advanced/05-并查集与LRUCache/README.md)
- [第 6 节 LRU 与 B 树](docs/advanced/06-LRU与B树/README.md)
- [第 7 节 B 树 2](docs/advanced/07-B树二/README.md)
- [第 8 节 图](docs/advanced/08-图/README.md)
- [第 9 节 图 2](docs/advanced/09-图二/README.md)

</details>

## 推荐放置方式

```text
Java 数据结构笔记/
├── README.md
├── docs/
│   ├── 01-数据结构基础准备知识/
│   ├── 02-JDK17与顺序表/
│   └── ...
└── src/
```

- `README.md`：项目首页和总目录。
- `docs/`：课程笔记，按章节编号排序。
- `src/`：Java 代码练习，可以按数据结构类型继续分包。

## 上传到 GitHub

如果这是一个新项目，可以在当前目录执行：

```bash
git init
git add .
git commit -m "init java data structure notes"
git branch -M main
git remote add origin https://github.com/你的用户名/你的仓库名.git
git push -u origin main
```

