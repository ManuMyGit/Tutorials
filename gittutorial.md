# GIT CONCEPTS 
### The working directory
The first tree we will examine is "The Working Directory". This tree is in sync with the local filesystem and is representative of the immediate changes made to content in files and directories. Let's see this example:

`$ echo 'hello git reset' > reset_lifecycle_file`

`$ git status`
`On branch master`
`Changes not staged for commit:`
`(use "git add ..." to update what will be committed)`
`(use "git checkout -- ..." to discard changes in working directory)`
`modified: reset_lifecycle_file`

Invoking git status shows that Git is aware of the changes to the file. These changes are currently a part of the first tree, "The Working Directory". Git status can be used to show changes to the Working Directory. They will be displayed in the red with a 'modified' prefix.

### Staging index
Next up is the 'Staging Index' tree. This tree is tracking Working Directory changes, that have been promoted with git add, to be stored in the next commit. This tree is a complex internal caching mechanism. Git generally tries to hide the implementation details of the Staging Index from the user.

### Commit history
The git commit command adds changes to a permanent snapshot that lives in the Commit History. This snapshot also includes the state of the Staging Index at the time of commit.

# SETTING UP A REPOSITORY
### git init
The `git init` command creates a new Git repository. It can be used to convert an existing, unversioned project to a Git repository or initialize a new, empty repository. Most other Git commands are not available outside of an initialized repository, so this is usually the first command you'll run in a new project. Executing `git init` creates a `.git` subdirectory in the current working directory, which contains all of the necessary Git metadata for the new repository.

 - Example 1: `git init` (transform the current directory into a Git repository).
 - Example 2: `git init <directory>` (create an empty Git repository in the specified directory).
 - Example 3: `git init --bare <directory>` (the `--bare` flag creates a repository that doesn’t have a working directory, making it impossible to edit files and commit changes in that repository. You would create a bare repository to git push and git pull from, but never directly commit to it. Central repositories should always be created as bare repositories because pushing branches to a non-bare repository has the potential to overwrite changes. Think of `--bare` as a way to mark a repository as a storage facility, as opposed to a development environment).

### git clone
`git clone` is a Git command line utility which is used to target an existing repository and create a clone, or copy of the target repository.

If a project has already been set up in a central repository, the `git clone` command is the most common way for users to obtain a development copy.

##### Repo-to-repo collaboration
It’s important to understand that Git’s idea of a “working copy” is very different from the working copy you get by checking out code from an SVN repository. Unlike SVN, Git makes no distinction between the working copy and the central repository. This makes collaborating with Git fundamentally different than with SVN. Whereas SVN depends on the relationship between the central repository and the working copy, Git’s collaboration model is based on repository-to-repository interaction. Instead of checking a working copy into SVN’s central repository, you push or pull commits from one repository to another.

 - Example 1: `git clone <repo>` (clone the repository located at `<repo>` into the current location on the local machine).
 - Example 2: `git clone <repo> <directory>` (same than before, but into a specific directory).
 - Example 3: `git clone --branch <tag> <repo>` (clone the repository located at `<repo>` and only clone the ref for `<tag>`).
 - Example 4: `git clone -depth = 1 <repo>` (clone the repository located at <repo> and only clone the history of commits specified by the option depth. In this case, only the most recent commit is included).
 - Example 5: `git clone -branch new_feature <repo>` (clone only the `new_feature` branch from the remote Git repository. This is purely a convince utility to save you time from downloading the `HEAD` ref of the repository and then having to additionally fetch the ref you need).

### git config
It is used to configure the repository. This command has several levels.
 - local: by default, this command will write to a local level if no configuration option is passed. Local level configuration is applied to the context repository **git config** gets invoked in. Local configuration values are stored in a file that can be found in the repo's .git directory: `.git/config`.
 - global: global level configuration is user-specific, meaning it is applied to an operating system user. Global configuration values are stored in a file that is located in a user's home directory. `~/.gitconfig` on unix systems and `C:\Users\<username>\.gitconfig` on windows.
 - system: system-level configuration is applied across an entire machine. This covers all users on an operating system and all repos.

Let's see a few examples:
 - Example 1: `git config --global user.name "anyName"` (config name of the repository for commits).
 - Example 2: `git config --global user.email anyEmail` (config email of the repository for commits).
 - Example 3: `git config --global core.editor "subl -n -w"` (set sublime text as default editor when a command requires some input).
 - Example 4: `git config --global pager.branch false` (change output of commands, such as branch, to terminal).

### git alias
It is important to note that there is no direct git alias command. Aliases are created through the use of the git config command and the Git configuration files. As with other configuration values, aliases can be created in a local or global scope.

Let's show several examples:
 - Example 1: `git config --global alias.co checkout`
 - Example 2: `git config --global alias.br branch`
 - Example 3: `git config --global alias.cm commit`
 - Example 4: `git config --global alias.st status`

The previous code example creates globally stored shortcuts for common git commands. Creating the aliases will not modify the source commands. So git checkout will still be available even though we now have the git co alias.

# SAVING CHANGES
### git add
The git add command adds a change in the working directory to the staging area. It tells Git that you want to include updates to a particular file in the next commit. However, git add doesn't really affect the repository in any significant way—changes are not actually recorded until you run git commit.

The git add and git commit commands compose the fundamental Git workflow. These are the two commands that every Git user needs to understand, regardless of their team’s collaboration model.

Developing a project revolves around the basic edit/stage/commit pattern. First, you edit your files in the working directory. When you’re ready to save a copy of the current state of the project, you stage changes with git add. After you’re happy with the staged snapshot, you commit it to the project history with git commit. The git reset command is used to undo a commit or staged snapshot.

In addition to git add and git commit, a third command git push is essential for a complete collaborative Git workflow. git push is utilized to send the committed changes to remote repositories for collaboration. This enables other team members to access a set of saved changes.

##### The staging area
The primary function of the git add command, is to promote pending changes in the working directory, to the git staging area. The staging area is one of Git's more unique features. It helps to think of it as a buffer between the working directory and the project history. The staging area is considered one of the "three trees" of Git, along with, the working directory, and the commit history.

Example 1: `git add <file>` (stage all changes in file for next commit).
Example 2: `git add <directory>` (state all changes in all files from directory for next commit).
Example 3: `git add -p` (begin an interactive staging session that lets you choose portions of a file to add to the next commit. Use y to stage the chunk, n to ignore the chunk, s to split it into smaller chunks, e to manually edit the chunk, and q to exit).

### git commit
While they share the same name, git commit is nothing like svn commit. This shared term can be a point of confusion for Git newcomers who have a svn background, and it is important to emphasize the difference. To compare git commit vs svn commit is to compare a centralized application model (svn) vs a distributed application model (Git). In SVN, a commit pushes changes from the local SVN client, to a remote centralized shared SVN repository. In Git, repositories are distributed, Snapshots are committed to the local repository, and this requires absolutely no interaction with other Git repositories. Git commits can later be pushed to arbitrary remote repositories.

Aside from the practical distinctions between SVN and Git, their underlying implementation also follows entirely divergent design philosophies. Whereas SVN tracks differences of a file, Git’s version control model is based on snapshots. For example, a SVN commit consists of a diff compared to the original file added to the repository. Git, on the other hand, records the entire contents of each file in every commit.

 - Example 1: `git commit` (commit the staged snapshot).
 - Example 2: `git commit -a` (commit a snapshot of all changes in the working directory. This only includes modifications to tracked files, those that have been added with git add at some point in their history).
 - Example 3: `git commit -m "message"` (a shortcut command that immediately creates a commit with a passed commit message).
 - Example 4: `git commit -am "message"` (a power user shortcut command that combines the -a and -m options. This combination immediately creates a commit of all the staged changes and takes an inline commit message).
 - Example 5: `git commit --amend` (this option adds another level of functionality to the commit command. Passing this option will modify the last commit. Instead of creating a new commit, staged changes will be added to the previous commit).

### git diff
Diffing is a function that takes two input data sets and outputs the changes between them. git diff is a multi-use Git command that when executed runs a diff function on Git data sources. These data sources can be commits, branches, files and more. This document will discuss common invocations of git diff and diffing work flow patterns. The git diff command is often used along with git status and git log to analyze the current state of a Git repo.

### .gitignore
Git sees every file in your working copy as one of three things:

 1. tracked - a file which has been previously staged or committed.
 2. untracked - a file which has not been staged or committed.
 3. ignored - a file which Git has been explicitly told to ignore.

Ignored files are usually build artifacts and machine generated files that can be derived from your repository source or should otherwise not be committed.

# INSPECTING A REPOSITORY
### git status
The git status command displays the state of the working directory and the staging area. It lets you see which changes have been staged, which haven’t, and which files aren’t being tracked by Git. Status output does not show you any information regarding the committed project history. For this, you need to use git log.

The git status command is a relatively straightforward command. It simply shows you what's been going on with git add and git commit. Status messages also include relevant instructions for staging/unstaging files.

### git log
The git log command displays committed snapshots. It lets you list the project history, filter it, and search for specific changes. While git status lets you inspect the working directory and the staging area, git log only operates on the committed history.

 - Example 1: `git log` (display the entire commit history using the default formatting. If the output takes up more than one screen, you can use Space to scroll and q to exit).
 - Example 2: `git log -n <limit>` (limit the number of commits by <limit>. For example, git log -n 3 will display only 3 commits).
 - Example 3: `git log --oneline` (condense each commit to a single line. This is useful for getting a high-level overview of the project history..
 - Example 4: `git log --stat` (along with the ordinary git log information, include which files were altered and the relative number of lines that were added or deleted from each of them).
 - Example 5: `git log -p*` (display the patch representing each commit. This shows the full diff of each commit, which is the most detailed view you can have of your project history).
 - Example 6: `git log <file>` (only display commits that include the specified file. This is an easy way to see the history of a particular file).
 - Example 7: `git log --graph --decorate --oneline` (a few useful options to consider. The --graph flag that will draw a text based graph of the commits on the left hand side of the commit messages. --decorate adds the names of branches or tags of the commits that are shown. --oneline shows the commit information on a single line making it easier to browse through commits at-a-glance).

### git tag
Tags are ref's that point to specific points in Git history. Tagging is generally used to capture a point in history that is used for a marked version release (i.e. v1.0.1). A tag is like a branch that doesn’t change. Unlike branches, tags, after being created, have no further history of commits.

 - Example 1: `git tag <tagname>` (create a tag named tagname).

Git supports two different types of tags, annotated and lightweight tags.

##### Anotated tags
Annotated tags are stored as full objects in the Git database. To reiterate, They store extra meta data such as: the tagger name, email, and date. Similar to commits and commit messages Annotated tags have a tagging message.

 - Example 1: `git tag -a v1.4` (it will create a new annotated tag identified with v1.4).
 - Example 2: `git tag -a v1.4 -m "my version 1.4"` (similar to the previous invocation, however, this version of the command is passed the -m option and a message).

##### Lightweight tags
Lightweight tags are created with the absence of the -a, -s, or -m options. Lightweight tags create a new tag checksum and store it in the .git/ directory of the project's repo.

 - Example 1: `git tag v1.4-lw` (it creates a lightweight tag identified as v1.4-lw).

# UNDOING CHANGES
### git checkout
Git checkout is an easy way to “load” any saved snapshots onto your development machine. During the normal course of development, the HEAD usually points to master or some other local branch, but when you check out a previous commit, HEAD no longer points to a branch—it points directly to a commit. Checking out an old file does not move the HEAD pointer. It remains on the same branch and same commit, avoiding a 'detached head' state. You can then commit the old version of the file in a new snapshot as you would any other changes. So, in effect, this usage of git checkout on a file, serves as a way to revert back to an old version of an individual file.

Let's assume we have this project (with the command get logs --online):

`b7119f2 Continue doing crazy things`
`872fa7e Try something crazy`
`a1e8fb5 Make some important changes to hello.txt`
`435b61d Create hello.txt`
`9773e52 Initial import`

You can use git checkout to view the “Make some import changes to hello.txt” commit as follows:

`git checkout a1e8fb5`

This makes your working directory match the exact state of the a1e8fb5 commit. You can look at files, compile the project, run tests, and even edit files without worrying about losing the current state of the project. Nothing you do in here will be saved in your repository. To continue developing, you need to get back to the “current” state of your project:

`git checkout master`

##### Udoing changes using git checkout
Using the git checkout command we can checkout the previous commit, a1e8fb5, putting the repository in a state before the crazy commit happened. From this state (the detached HEAD state), we can execute `git checkout -b new_branch_without_crazy_commit`. This will create a new branch named new_branch_without_crazy_commit and switch to that state. The repo is now on a new history timeline in which the 872fa7e commit no longer exists. At this point, we can continue work on this new branch in which the 872fa7e commit no longer exists and consider it 'undone'. Unfortunately, if you need the previous branch, maybe it was your master branch, this undo strategy is not appropriate.

##### Udoing changes using git revert
This time let's try a revert 'undo'. If we execute `git revert HEAD`, Git will create a new commit with the inverse of the last commit. This adds a new commit to the current branch history and now makes it look like:

`git log --oneline`
`e2f9a78 Revert "Try something crazy"`
`872fa7e Try something crazy`
`a1e8fb5 Make some important changes to hello.txt`
`435b61d Create hello.txt`
`9773e52 Initial import`

At this point, we have again technically 'undone' the 872fa7e commit. Although 872fa7e still exists in the history, the new e2f9a78 commit is an inverse of the changes in 872fa7e. Unlike our previous checkout strategy, we can continue using the same branch. This solution is a satisfactory undo. This is the ideal 'undo' method for working with public shared repositories.

##### Udoing changes using git reset
If we invoke `git reset --hard a1e8fb5` the commit history is reset to that specified commit. Examining the commit history with git log will now look like:

`git log --oneline`
`a1e8fb5 Make some important changes to hello.txt`
`435b61d Create hello.txt`
`9773e52 Initial import`

The log output shows the e2f9a78 and 872fa7e commits no longer exist in the commit history. At this point, we can continue working and creating new commits as if the 'crazy' commits never happened. This method of undoing changes has the cleanest effect on history. Doing a reset is great for local changes however it adds complications when working with a shared remote repository. If we have a shared remote repository that has the 872fa7e commit pushed to it, and we try to git push a branch where we have reset the history, Git will catch this and throw an error. Git will assume that the branch being pushed is not up to date because of it's missing commits. In these scenarios, git revert should be the preferred undo method.

### git clean
Git clean can be considered complementary to other commands like git reset and git checkout. Whereas these other commands operate on files previously added to the Git tracking index, the git clean command operates on untracked files. Untracked files are files that have been created within your repo's working directory but have not yet been added to the repository's tracking index using the git add command.

 - Example 1: `git clean -n` (the -n option will perform a “dry run” of git clean. This will show you which files are going to be removed without actually removing them. It is a best practice to always first perform a dry run of git clean).
 - Example 2: `git clean -f` (the force option initiates the actual deletion of untracked files from the current directory. Force is required unless the clean.requireForce configuration option is set to false. This will not remove untracked folders or files specified by .gitignore. By default git clean -f will operate on all the current directory untracked files. Additionally, a <path> value can be passed with the -f option that will remove a specific file).
 - Example 3: `git clean -d` (the -d option tells git clean that you also want to remove any untracked directories, by default it will ignore directories).

## git revert
The git revert command can be considered an 'undo' type command, however, it is not a traditional undo operation. Instead of removing the commit from the project history, it figures out how to invert the changes introduced by the commit and appends a new commit with the resulting inverse content. This prevents Git from losing history, which is important for the integrity of your revision history and for reliable collaboration.

Reverting should be used when you want to apply the inverse of a commit from your project history. This can be useful, for example, if you’re tracking down a bug and find that it was introduced by a single commit. Instead of manually going in, fixing it, and committing a new snapshot, you can use git revert to automatically do all of this for you.

To demonstrate let’s create an example repo using the command line examples below:
`$ mkdir git_revert_test`
`$ cd git_revert_test/`
`$ git init .`
`Initialized empty Git repository in /git_revert_test/.git/`
`$ touch demo_file`
`$ git add demo_file`
`$ git commit -am"initial commit"`
`[master (root-commit) 299b15f] initial commit`
`1 file changed, 0 insertions(+), 0 deletions(-)`
`create mode 100644 demo_file`
`$ echo "initial content" >> demo_file`
`$ git commit -am"add new content to demo file"`
`[master 3602d88] add new content to demo file`
`n 1 file changed, 1 insertion(+)`
`$ echo "prepended line content" >> demo_file`
`$ git commit -am"prepend content to demo file"`
`[master 86bb32e] prepend content to demo file`
`1 file changed, 1 insertion(+)`
`$ git log --oneline`
`86bb32e prepend content to demo file`
`3602d88 add new content to demo file`
`299b15f initial commit`

Here we have initialized a repo in a newly created directory named git_revert_test. We have made 3 commits to the repo in which we have added a file demo_file and modified its content twice. At the end of the repo setup procedure, we invoke git log to display the commit history, showing a total of 3 commits. With the repo in this state, we are ready to initiate a `git revert`.

`$ git revert HEAD`
`[master b9cd081] Revert "prepend content to demo file"`
`1 file changed, 1 deletion(-)`

Git revert expects a commit ref was passed in and will not execute without one. Here we have passed in the HEAD ref. This will revert the latest commit.

##### Resetting vs reverting
It's important to understand that git revert undoes a single commit. It does not "revert" back to the previous state of a project by removing all subsequent commits. In Git, this is actually called a reset, not a revert.

### git reset
The git reset command is a complex and versatile tool for undoing changes. It has three primary forms of invocation. These forms correspond to command line arguments --soft, --mixed, --hard. The three arguments each correspond to Git's three internal state management mechanism's, The Commit Tree (HEAD), The Staging Index, and The Working Directory.

At a surface level, git reset is similar in behavior to git checkout. Where git checkout solely operates on the HEAD ref pointer, git reset will move the HEAD ref pointer and the current branch ref pointer.

Let's see this example: `a -> b -> c -> d (HEAD, master)`

This example demonstrates a sequence of commits on the master branch. The HEAD ref and master branch ref currently point to commit d. Now let us execute and compare, both git checkout b and git reset b.

 - Example 1: `git checkout b` (the master ref is still pointing to d. The HEAD ref has been moved, and now points at commit b. The repo is now in a 'detached HEAD' state. `a -> b (HEAD) -> c -> d (master)`).
 - Example 1: `git reset b` (git reset, moves both the HEAD and branch refs to the specified commit. `a -> b (HEAD, master) c -> d`).

In addition to updating the commit ref pointers, git reset will modify the state of the three trees. The ref pointer modification always happens and is an update to the third tree, the Commit tree. The command line arguments --soft, --mixed, and --hard direct how to modify the Staging Index, and Working Directory trees.

##### Main options
The default invocation of git reset has implicit arguments of --mixed and HEAD. This means executing git reset is equivalent to executing git reset --mixed HEAD. In this form HEAD is the specified commit. Instead of HEAD any Git SHA-1 commit hash can be used.
 - `--hard`: this is the most direct, DANGEROUS, and frequently used option. When passed --hard The Commit History ref pointers are updated to the specified commit. Then, the Staging Index and Working Directory are reset to match that of the specified commit. Any previously pending changes to the Staging Index and the Working Directory gets reset to match the state of the Commit Tree. This means any pending work that was hanging out in the Staging Index and Working Directory will be lost.
 - `--mixed`: this is the default operating mode. The ref pointers are updated. The Staging Index is reset to the state of the specified commit. Any changes that have been undone from the Staging Index are moved to the Working Directory.
 - `--soft`: when the --soft argument is passed, the ref pointers are updated and the reset stops there. The Staging Index and the Working Directory are left untouched.

# SYNCING
### git remote
The git remote command lets you create, view, and delete connections to other repositories. Remote connections are more like bookmarks rather than direct links into other repositories. Instead of providing real-time access to another repository, they serve as convenient names that can be used to reference a not-so-convenient URL.

 - Example 1: `git remoted` (list the remote connections you have to other repositories).
 - Example 2: `git remote -v` (same as the above command, but include the URL of each connection).
 - Example 3: `git remote add manu http://www.whatever.com/manu.git` (create a new connection to a remote repository. After adding a remote, you’ll be able to use manu as a convenient shortcut for the url in other Git commands).
 - Example 4: `git remote rm <name>` (remove the connection to the remote repository called <name>).
 - Example 5: `git remote rename <old-name> <new-name>` (rename a remote connection from <old-name> to <new-name>).
 - Example 6: `git remote show origin` (it shows all information about origin repository, listed by git remote previously).
 - Example 9: `git remote prune origin` (update local git cache).

When you clone a repository with git clone, it automatically creates a remote connection called origin pointing back to the cloned repository. This is useful for developers creating a local copy of a central repository, since it provides an easy way to pull upstream changes or publish local commits. This behavior is also why most Git-based projects call their central repository origin.

### git fetch
The git fetch command downloads commits, files, and refs from a remote repository into your local repo. Fetching is what you do when you want to see what everybody else has been working on. It’s similar to svn update in that it lets you see how the central history has progressed, but it doesn’t force you to actually merge the changes into your repository. Git isolates fetched content as a from existing local content, it has absolutely no effect on your local development work. Fetched content has to be explicitly checked out using the git checkout command. This makes fetching a safe way to review commits before integrating them with your local repository.

 - Example 1: `git fetch` (same as next example).
 - Example 2: `git fetch <remote>` (fetch all of the branches from the repository. This also downloads all of the required commits and files from the other repository).
 - Example 3: `git fetch <remote> <branch>` (same than above, but for a specific branch).
 - Example 4: `git fetch --all` (a power move which fetches all registered remotes and their branches).
 - Example 5: `git fetch --dry-run` (the --dry-run option will perform a demo run of the command. It will output examples of actions it will take during the fetch but not apply them).

### git pull
The git pull command is used to fetch and download content from a remote repository and immediately update the local repository to match that content. Merging remote upstream changes into your local repository is a common task in Git-based collaboration work flows. The git pull command is actually a combination of two other commands, git fetch followed by git merge. In the first stage of operation git pull will execute a git fetch scoped to the local branch that HEAD is pointed at. Once the content is downloaded, git pull will enter a merge workflow. A new merge commit will be-created and HEAD updated to point at the new commit.

 - Example 1: `git pull <remote>` (fetch the specified remote’s copy of the current branch and immediately merge it into the local copy. This is the same as git fetch <remote> followed by git merge origin/<current-branch>).
 - Example 2: `git pull --no-commit <remote>` (similar to the default invocation, fetches the remote content but does not create a new merge commit).
 - Example 3: `git pull --rebase <remote>` (same as the previous pull Instead of using git merge to integrate the remote branch with the local one, use git rebase).
 - Example 4: `git pull --verbose` (gives verbose output during a pull which displays the content being downloaded and the merge details).

### git push
The git push command is used to upload local repository content to a remote repository. Pushing is how you transfer commits from your local repository to a remote repo. It's the counterpart to git fetch, but whereas fetching imports commits to local branches, pushing exports commits to remote branches. Remote branches are configured using the git remote command. Pushing has the potential to overwrite changes, caution should be taken when pushing.

 - Example 1: `git push <remote> <branch>` (push the specified branch to <remote>, along with all of the necessary commits and internal objects. This creates a local branch in the destination repository. To prevent you from overwriting commits, Git won’t let you push when it results in a non-fast-forward merge in the destination repository).
 - Example 2: `git push <remote> --force` (same as the above command, but force the push even if it results in a non-fast-forward merge. Do not use the --force flag unless you’re absolutely sure you know what you’re doing).
 - Example 3: `git push <remote> --all` (push all of your local branches to the specified remot).
 - Example 4: `git push <remote> --tags` (tags are not automatically pushed when you push a branch or use the --all option. The --tags flag sends all of your local tags to the remote repository).
 - Example 5: git push -u <remote> <branch> (it creates the branch into the origin remote repository).
 - Example 6: git push -ud <remote> <branch> (it creates the branch into the origin remote repository and then removes the local branch).
 - Example 7: git push <remote> -d <branch> (it removes the remote branch).

# MAKING A PULL REQUEST (PR)
### How it works
Pull requests can be used in conjunction with the Feature Branch Workflow, the Gitflow Workflow, or the Forking Workflow. But a pull request requires either two distinct branches or two distinct repositories, so they will not work with the Centralized Workflow. Using pull requests with each of these workflows is slightly different, but the general process is as follows:
 1. A developer creates the feature in a dedicated branch in their local repo.
 2. The developer pushes the branch to a public Bitbucket repository.
 3. The developer files a pull request via Bitbucket.
 4. The rest of the team reviews the code, discusses it, and alters it.
 5. The project maintainer merges the feature into the official repository and closes the pull request.

##### The project maintainer merges the feature into the official repository and closes the pull request.
The Feature Branch Workflow uses a shared Bitbucket repository for managing collaboration, and developers create features in isolated branches. But, instead of immediately merging them into master, developers should open a pull request to initiate a discussion around the feature before it gets integrated into the main codebase.
There is only one public repository in the Feature Branch Workflow, so the pull request’s destination repository and the source repository will always be the same. Typically, the developer will specify their feature branch as the source branch and the master branch as the destination branch.

##### Gitflow Workflow With Pull Requests
The Gitflow Workflow is similar to the Feature Branch Workflow, but defines a strict branching model designed around the project release. Adding pull requests to the Gitflow Workflow gives developers a convenient place to talk about a release branch or a maintenance branch while they’re working on it.
The mechanics of pull requests in the Gitflow Workflow are the exact same as the previous section: a developer simply files a pull request when a feature, release, or hotfix branch needs to be reviewed, and the rest of the team will be notified via Bitbucket. Features are generally merged into the develop branch, while release and hotfix branches are merged into both develop and master. Pull requests can be used to formally manage all of these merges.

##### Forking Workflow With Pull Requests
In the Forking Workflow, a developer pushes a completed feature to their own public repository instead of a shared one. After that, they file a pull request to let the project maintainer know that it’s ready for review. The notification aspect of pull requests is particularly useful in this workflow because the project maintainer has no way of knowing when another developer has added commits to their Bitbucket repository.
Since each developer has their own public repository, the pull request’s source repository will differ from its destination repository.

### Example
In the example, Manu is a developer, and Carmen is the project maintainer. Both of them have their own public Bitbucket repositories, and Carmen’s contains the official project.

 1. To start working in the project, Manu first needs to fork John’s Bitbucket repository.
 2. Next, Manu needs to clone the Bitbucket repository that she just forked. This will give her a working copy of the project on her local machine.
 3. Before she starts writing any code, Manu needs to create a new branch for the feature. This branch is what she will use as the source branch of the pull request. Manu can use as many commits as she needs to create the feature.
 4. After her feature is complete, Manu pushes the feature branch to her own Bitbucket repository (not the official repository) with a simple git push.
 5. After Bitbucket has her feature branch, Manu can create the pull request through her Bitbucket account by navigating to her forked repository and clicking the Pull request.
 6. Carmen can access all of the pull requests people have filed by clicking on the Pull request tab in his own Bitbucket repository. Clicking on Manu’s pull request will show him a description of the pull request, the feature’s commit history, and a diff of all the changes it contains.

# USING BRANCHES
A branch represents an independent line of development. Branches serve as an abstraction for the edit/stage/commit process. You can think of them as a way to request a brand new working directory, staging area, and project history. New commits are recorded in the history for the current branch, which results in a fork in the history of the project.

### git branch
The git branch command lets you create, list, rename, and delete branches. It doesn’t let you switch between branches or put a forked history back together again. For this reason, git branch is tightly integrated with the git checkout and git merge commands.

 - Example 1: `git branch` (list all of the branches in your repository. This is synonymous with git branch --list).
 - Example 2: `git branch <branch>` (create a new branch called <branch>. This does not check out the new branch).
 - Example 3: `git branch -d <branch>` (delete the specified branch. This is a “safe” operation in that Git prevents you from deleting the branch if it has unmerged changes).
 - Example 4: `git branch -D <branch>` (force delete the specified branch, even if it has unmerged changes. This is the command to use if you want to permanently throw away all of the commits associated with a particular line of development).
 - Example 5: git push <remote> -d <branch> (it removes the remote branch).
 - Example 6: `git branch -m <branch>` (rename the current branch to <branch>).
 - Example 7: `git branch -r` (list all remote branches).
 - Example 8: `git branch -a` (list all branches, both remote and local).
 - Example 9: `git remote prune origin` (update local git cache).

##### Creating branches
It's important to understand that branches are just pointers to commits. When you create a branch, all Git needs to do is create a new pointer, it doesn’t change the repository in any other way.

### git checkout
The git checkout command lets you navigate between the branches created by git branch. Checking out a branch updates the files in the working directory to match the version stored in that branch, and it tells Git to record all new commits on that branch. Think of it as a way to select which line of development you’re working on.
Having a dedicated branch for each new feature is a dramatic shift from a traditional SVN workflow. It makes it ridiculously easy to try new experiments without the fear of destroying existing functionality, and it makes it possible to work on many unrelated features at the same time. In addition, branches also facilitate several collaborative workflows.

 - Example 1: `git checkout feature_inprogress_branch` (change to the branch feature_inprogress_branch).
 - Example 2: `git checkout -b <new-branch>` (it creates a new branch called <new-branch> and then changes to it. It is a shortcut for git branch and git checkout).
 - Example 3: `git checkout -b <new-branch> <existing-branch>` (by default git checkout -b will base the new-branch off the current HEAD. An optional additional branch parameter can be passed to git checkout. In this example, <existing-branch> is passed which then bases new-branch off of existing-branch instead of the current HEAD).

### git merge
Git merge will combine multiple sequences of commits into one unified history. In the most frequent use cases, git merge is used to combine two branches. Once Git finds a common base commit it will create a new "merge commit" that combines the changes of each queued merge commit sequence.

##### Preparing to merge
Before performing a merge there are a couple of preparation steps to take to ensure the merge goes smoothly.
 1. Confirm the receiving branch: execute git status to ensure that HEAD is pointing to the correct merge-receiving branch. If needed, execute git checkout <receiving> to switch to the receiving branch. In our case we will execute git checkout master.
 2. Fetch latest remote commits: execute git fetch to pull the latest remote commits. Once the fetch is completed ensure the master branch has the latest updates by executing git pull.
 3. Merging: once the previously discussed "preparing to merge" steps have been taken a merge can be initiated by executing git merge <branch name>, where <branch name> is the name of the branch that will be merged into the receiving branch.

# GIT WORKFLOWS
### Centralized workflow
The Centralized Workflow is a great Git workflow for teams transitioning from SVN. Like Subversion, the Centralized Workflow uses a central repository to serve as the single point-of-entry for all changes to the project. Instead of trunk, the default development branch is called master and all changes are committed into this branch. This workflow doesn’t require any other branches besides master.

### Git Feature Branch Workflow
The Feature Branch Workflow assumes a central repository, and master represents the official project history. Instead of committing directly on their local master branch, developers create a new branch every time they start work on a new feature. Feature branches should have descriptive names, like animated-menu-items or issue-#1061. The idea is to give a clear, highly-focused purpose to each branch. Git makes no technical distinction between the master branch and feature branches, so developers can edit, stage, and commit changes to a feature branch.

In addition, feature branches can (and should) be pushed to the central repository. This makes it possible to share a feature with other developers without touching any official code. Since master is the only “special” branch, storing several feature branches on the central repository doesn’t pose any problems. Of course, this is also a convenient way to back up everybody’s local commits. The following is a walk-through of the life-cycle of a feature branch.

### Gitflow Workflow
Gitflow is really just an abstract idea of a Git workflow. This means it dictates what kind of branches to set up and how to merge them together. The git-flow toolset is an actual command line tool that has an installation process. After installing git-flow you can use it in your project by executing git flow init. Git-flow is a wrapper around Git.

##### Develop and master branches
Instead of a single master branch, this workflow uses two branches to record the history of the project. The master branch stores the official release history, and the develop branch serves as an integration branch for features. It's also convenient to tag all commits in the master branch with a version number.

The first step is to complement the default master with a develop branch. A simple way to do this is for one developer to create an empty develop branch locally and push it to the server.

`git branch develop`
`git push -u origin develop`

This branch will contain the complete history of the project, whereas master will contain an abridged version. Other developers should now clone the central repository and create a tracking branch for develop.

When using the git-flow extension library, executing git flow init on an existing repo will create the develop branch.

##### Feature branches
Each new feature should reside in its own branch, which can be pushed to the central repository for backup/collaboration. But, instead of branching off of master, feature branches use develop as their parent branch. When a feature is complete, it gets merged back into develop. Features should never interact directly with master.

To create a new feature branch without git flow extension:

`git checkout develop`
`git checkout -b feature_branch`

When using the git-flow extension:

`git flow feature start feature_branch`

To finish a feature branch without git flow extension:

`git checkout develop`
`git merge feature_branch`

When using the git flow extension:

`git flow feature finish feature_branch`

##### Release Branches
Once develop has acquired enough features for a release (or a predetermined release date is approaching), you fork a release branch off of develop. Creating this branch starts the next release cycle, so no new features can be added after this point—only bug fixes, documentation generation, and other release-oriented tasks should go in this branch. Once it's ready to ship, the release branch gets merged into master and tagged with a version number. In addition, it should be merged back into develop, which may have progressed since the release was initiated.

A new release branch can be created using the following methods. Without the git-flow extensions:

`git checkout develop`
`git checkout -b release/0.1.0`

When using the git-flow extensions:

`git flow release start 0.1.0`

Once the release is ready to ship, it will get merged it into master and develop, then the release branch will be deleted. It’s important to merge back into develop because critical updates may have been added to the release branch and they need to be accessible to new features. To finish a release branch, use the following methods. Without the git-flow extensions:

`git checkout master`
`git merge release/0.1.0`

Or with the git-flow extension:

`git flow release finish '0.1.0'`

##### Hotfix Branches
Maintenance or “hotfix” branches are used to quickly patch production releases. Hotfix branches are a lot like release branches and feature branches except they're based on master instead of develop. This is the only branch that should fork directly off of master. As soon as the fix is complete, it should be merged into both master and develop (or the current release branch), and master should be tagged with an updated version number.

A hotfix branch can be created using the following methods. Without the git-flow extensions:

`git checkout master`
`git checkout -b hotfix_branch`

When using the git-flow extensions: 

`git flow hotfix start hotfix_branch`

Similar to finishing a release branch, a hotfix branch gets merged into both master and develop.

`git checkout master`
`git merge hotfix_branch`
`git checkout develop`
`git merge hotfix_branch`
`git branch -D hotfix_branch`

When using the git-flow extensions:

`git flow hotfix finish hotfix_branch`

##### GitHub
GitHub is an image repository. To use it, it is as easy as:
 1. To login: docker login --username=XXX --password=YYY.
 2. To push the image: docker push repo:tag.
 3. GitHub allows to use 1 repo for free, so if you want to use the same repo for different projects, tag must be used. For instance: repo:restapi, repo:soapapi, ... Even versions could be used.
