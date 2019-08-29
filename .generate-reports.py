#!/usr/bin/env python
# -*- coding: UTF-8 -*-
# fancy comments come at a cost!

# Script to generate a Maven site and push reports to a branch (gh-pages by default).
# This script assumes that both git and Maven have been installed and that the following environment variables
# are defined:
#   - REPORTS_GITHUB_ACCESS_TOKEN: GitHub personal access token used to push generated reports
#   - REPORTS_GITHUB_USERNAME: username used to push generated reports
# 
# Yes, these could be passed as arguments, but Travis log would print them out.

# Output of this script is to populate the gh-pages branch with the reports generated by running "mvn site".
# The structure of the generated reports is similar to:
# 
# (branch gh-pages)         # pages_branch option
# reports                   # base_output_dir option
#   ├── development         # output_dir positional argument
#   │   ├── index.html
#   │   ├── pmd.html
#   │   ├── jacoco.html
#   │   └── ...
#   │  
#   ├── 1.0.0               # output_dir positional argument 
#   │   ├── index.html
#   │   ├── pmd.html
#   │   ├── jacoco.html
#   │   └── ...
#   │  
#   ├── 1.0.1               # output_dir positional argument
#   │   ├── index.html
#   │   ├── pmd.html
#   │   ├── jacoco.html
#   │   └── ...
#   │  
#   └── 2.0.0               # output_dir positional argument
#       ├── index.html
#       ├── pmd.html
#       ├── jacoco.html
#       └── ...
#
# So only one "development" version of the reports is maintained, while reports for all 
# tagged commits--assumed to be releases--are maintained on the gh-pages branch. 
# 
# The content of each of the folders is whatever Maven generates on the target/site folder.


import argparse, os, shutil, subprocess, tempfile, sys, re

# folder where maven outputs reports generated by running "mvn site"
MAVEN_SITE_DIR = os.path.join('target', 'site')
# base directory where reports will be copied to
BASE_REPORT_DIR = 'reports'
# credentials are given via environment variables
TOKEN_ENV_VARIABLE_NAME = 'REPORTS_GITHUB_ACCESS_TOKEN'
# compiled regex to match files that should not be deleted when cleaning the working folder (in gh-pages)
UNTOUCHABLE_FILES_MATCHER = re.compile('^\.git.*')
# regex to validate output folder
REPORTS_VERSION_REGEX = '^(development|[vV]?\d+\.\d+\.\d+)$'


# parses arguments and does the thing
def main():
    parser = argparse.ArgumentParser(description='QBiC Javadoc Generator.', prog='generate-javadocs.py', formatter_class=argparse.ArgumentDefaultsHelpFormatter)
    parser.add_argument('-s', '--site-dir', default=MAVEN_SITE_DIR,
        help='Directory where Maven reports are found (output of running \'mvn site\').')
    parser.add_argument('-b', '--base-output-dir', default=BASE_REPORT_DIR,
        help='Base directory where the reports will be copied.')    
    parser.add_argument('-p', '--pages-branch', default="gh-pages",
        help='Name of the git branch on which the reports will be pushed.')
    parser.add_argument('-a', '--access-token-var-name', default=TOKEN_ENV_VARIABLE_NAME,
        help='Name of the environment variable holding the GitHub personal access token used to push changes in reports.')
    parser.add_argument('-r', '--validation-regex', default=REPORTS_VERSION_REGEX,
        help='Regular expression to validate output_dir; it is assumed that report folders are named after a version.')
    parser.add_argument('--dry-run', action='store_true',
        help='If present, no changes to the remote repository (git commit/push) will be executed.')
    parser.add_argument('--skip-cleanup', action='store_true',
        help='Whether cleanup tasks (removing cloned repos) should be skipped.')
    parser.add_argument('output_dir', 
        help='Name of the folder, relative to the base output directory, where reports will be copied to. \
              This folder will be first cleared of its contents before the generated reports are copied. \
              Recommended values are: "development" or a valid release version string (e.g., 1.0.1)')
    parser.add_argument('repo_slug', help='Slug of the repository for which reports are being built.')
    parser.add_argument('commit_message', nargs='+', help='Message(s) to use when committing changes.')
    args = parser.parse_args()

    # check that the required environment variables have been defined
    try:
        validateArguments(args)
    except Exception as e:
        print('Error: {}'.format(str(e)), file=sys.stderr)
        exit(1)

    # since this will run on Travis, we cannot assume that we can change the current local repo without breaking anything
    # the safest way would be to clone this same repository on a temporary folder and leave the current local repo alone
    working_dir = tempfile.mkdtemp()
    clone_self(working_dir, args)
    
    # reports are available only in a specific branch
    force_checkout_pages_branch(working_dir, args)

    # since new branches have a parent commit, we have to remove everything but:
    #  * important files (e.g., .git) 
    #  * the base output directory (args.base_output_dir) 
    # otherwise, the newly created gh-pages branch will contain other non-report files!
    # also, it is a good idea to remove everything, since we don't want lingering unused report files
    remove_unneeded_files(working_dir, args)

    # move rports to their place
    prepare_report_dir(working_dir, args)

    # add, commit, push
    push_to_pages_branch(working_dir, args)

    # clean up
    if args.skip_cleanup:
        print('Skipping cleanup of working folder {}'.format(working_dir))    
    else:
        print('Removing working folder {}'.format(working_dir))
        shutil.rmtree(working_dir)


# Sanity check
def validateArguments(args):
    # check that the required environment variables are present
    if not args.access_token_var_name in os.environ:
        raise Exception('At least one of the required environment variables is missing. See comments on .generate-reports.py for further information.')
    
    # check if the name of the output_dir matches the regex
    regex = re.compile(args.validation_regex)
    if not regex.match(args.output_dir):
        raise Exception('The provided output directory for the reports, {}, is not valid. It must match the regex {}'.format(args.output_dir, args.validation_regex))

    # check that the reports are where they should be (you never know!)
    if not os.path.exists(args.site_dir) or not os.path.isdir(args.site_dir):
        raise Exception('Maven site folder {} does not exist or is not a directory.'.format(args.site_dir))


# Clones this repo into the passed working directory, credentials are used because OAuth has a bigger quota
# plus, we will be pushing changes to gh-pages branch
def clone_self(working_dir, args, exit_if_fail=True):
    execute(['git', 'clone', 'https://{}:x-oauth-basic@github.com/{}'.format(os.environ[args.access_token_var_name], args.repo_slug), working_dir], 
        'Could not clone {} in directory {}'.format(args.repo_slug, working_dir), exit_if_fail)


# Checks out the branch where reports reside (gh-pages)
def force_checkout_pages_branch(working_dir, args):
    # we need to add the gh-pages branch if it doesn't exist (git checkout -b gh-pages),
    # but if gh-pages already exists, we need to checkout (git checkout gh-pages), luckily, 
    # "git checkout branch" fails if branch doesn't exist
    print('Changing to branch {}'.format(args.pages_branch))
    try:
        execute(['git', '-C', working_dir, 'checkout', args.pages_branch], exit_if_fail=False)
    except:
        execute(['git', '-C', working_dir, 'checkout', '-b', args.pages_branch], 'Could not create branch {}'.format(args.pages_branch))


# Goes through the all files/folders (non-recursively) and deletes them using 'git rm'.
# Files that should not be deleted are ignored
def remove_unneeded_files(working_dir, args):
    print('Cleaning local repository ({}) of non-reports files'.format(working_dir))
    for f in os.listdir(working_dir):
        if should_delete(f, args):
            # instead of using OS calls to delete files/folders, use git rm to stage deletions
            print('    Deleting {} from {} branch'.format(f, args.pages_branch))
            execute(['git', '-C', working_dir, 'rm', '-r', '--ignore-unmatch', f], 'Could not remove {}.'.format(f))
            # files that are not part of the repository aren't removed by git and the --ignore-unmatch flag makes
            # git be nice so it doesn't exit with errors, so we need to force-remove them
            force_delete(os.path.join(working_dir, f))
        else:
            print('    Ignoring file/folder {}'.format(f))


# Prepares the report output directory, first by clearing it and then by moving the contents of target/site into it
def prepare_report_dir(working_dir, args):
    report_output_dir = os.path.join(working_dir, args.base_output_dir, args.output_dir)
    if os.path.exists(report_output_dir):
        if not os.path.isdir(report_output_dir):
            print('WARNING: Output destination {} exists and is not a directory.'.format(report_output_dir), file=sys.stderr)
        # remove the object from git
        print('Removing {}'.format(report_output_dir))
        execute(['git', '-C', working_dir, 'rm', '-r', '--ignore-unmatch', os.path.join(args.base_output_dir, args.output_dir)], 
                 'Could not remove {}.'.format(report_output_dir))
        # just in case git doesn't remove the file (if it wasn't tracked, for instance), force deletion using OS calls
        force_delete(report_output_dir)
    # we know the output folder doesn't exist, so we can recreate it
    print('Creating {}'.format(report_output_dir))
    os.makedirs(report_output_dir)

    # accidentally the whole target/site folder (well, yes, but actually, no, because we need only its contents)
    print('Moving contents of {} to {}'.format(args.site_dir, report_output_dir))
    for f in os.listdir(args.site_dir):
        print('    Moving {}'.format(f))
        shutil.move(os.path.join(args.site_dir, f), report_output_dir)


# Adds, commits and pushes changes
def push_to_pages_branch(working_dir, args):
    if args.dry_run:
        print('(running in dry run mode) Local/remote repository will not be modified')
    else:
        # add changes to the index
        print('Staging changes for commit')
        execute(['git', '-C', working_dir, 'add', '.'], 'Could not stage reports for commit.')

        # build the git-commit command and commit changes
        print('Pushing changes upstream')
        git_commit_command = ['git', '-C', working_dir, 'commit']
        for commit_message in args.commit_message:
            git_commit_command.extend(['-m', commit_message])
        execute(git_commit_command, 'Could not commit changes')

        # https://www.youtube.com/watch?v=vCadcBR95oU
        execute(['git', '-C', working_dir, 'push', '-u', 'origin', args.pages_branch], 'Could not push changes using provided credentials.')


# Whether it is safe to delete the given path, we won't delete important files/folders (such as .git)
# or the base output directory
def should_delete(path, args):
    return not UNTOUCHABLE_FILES_MATCHER.match(path) and path != args.base_output_dir


# Forcefully deletes recursively the passed file/folder using OS calls
def force_delete(file):
    if os.path.exists(file):
        if os.path.isdir(file):
            shutil.rmtree(file)
        else:
            os.remove(file)


# Executes an external command
# stderr/stdout are hidden to avoid leaking credentials into log files in Travis, so it might be a pain in the butt to debug, sorry, but safety first!
# if exit_if_fail is set to True, this method will print minimal stacktrace information and exit if a failure is encountered, otherwise, an exception 
# will be thrown (this is useful if an error will be handled by the invoking method)
def execute(command, error_message='Error encountered while executing command', exit_if_fail=True):
    # do not print the command, stderr or stdout! this might expose usernames/passwords/tokens!
    try:
        subprocess.run(command, check=True, stderr=subprocess.DEVNULL, stdout=subprocess.DEVNULL)
    except:
        if exit_if_fail:
            stack = traceback.extract_stack()
            try:
                print('{}\n  Error originated at file {}, line {}'.format(error_message, stack[-2].filename, stack[-2].lineno), file=sys.stderr)            
            except:
                print('{}\n  No information about the originating call is available.'.format(error_message), file=sys.stderr)
            exit(1)
        else:
            raise Exception()


        
if __name__ == "__main__":
    main()
