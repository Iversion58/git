git remote add origin git@github.com:Iversion58/learngit.git
git push -u origin master
git.It is a free software.
Creating a new branch is quick and simple.
I'm learing git and github.
git checkout -b dev
一是用git stash apply恢复，但是恢复后，stash内容并不删除，你需要用git stash drop来删除；

另一种方式是用git stash pop，恢复的同时把stash内容也删了
$ git log --graph --pretty=oneline --abbrev-commit	查看历史分支
$ git checkout -b 线程
git@github.com:Iversion58/git.git 远程仓库
echo # git >> README.md
git init
git add README.md
git commit -m "first commit"
git remote add origin git@github.com:Iversion58/git.git
git push -u origin master
准备合并dev分支，请注意--no-ff参数，表示禁用Fast forward：
$ git merge --no-ff -m "merge with no-ff" dev