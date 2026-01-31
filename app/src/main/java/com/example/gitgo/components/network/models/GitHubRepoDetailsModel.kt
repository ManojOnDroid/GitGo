package com.example.gitgo.components.network.models


import com.google.gson.annotations.SerializedName

data class GitHubRepoDetailsModel(
    @SerializedName("allow_auto_merge")
    val allowAutoMerge: Boolean?,
    @SerializedName("allow_forking")
    val allowForking: Boolean?,
    @SerializedName("allow_merge_commit")
    val allowMergeCommit: Boolean?,
    @SerializedName("allow_rebase_merge")
    val allowRebaseMerge: Boolean?,
    @SerializedName("allow_squash_merge")
    val allowSquashMerge: Boolean?,
    @SerializedName("archive_url")
    val archiveUrl: String?,
    val archived: Boolean?,
    @SerializedName("assignees_url")
    val assigneesUrl: String?,
    @SerializedName("blobs_url")
    val blobsUrl: String?,
    @SerializedName("branches_url")
    val branchesUrl: String?,
    @SerializedName("clone_url")
    val cloneUrl: String?,
    @SerializedName("collaborators_url")
    val collaboratorsUrl: String?,
    @SerializedName("comments_url")
    val commentsUrl: String?,
    @SerializedName("commits_url")
    val commitsUrl: String?,
    @SerializedName("compare_url")
    val compareUrl: String?,
    @SerializedName("contents_url")
    val contentsUrl: String?,
    @SerializedName("contributors_url")
    val contributorsUrl: String?,
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("default_branch")
    val defaultBranch: String?,
    @SerializedName("delete_branch_on_merge")
    val deleteBranchOnMerge: Boolean?,
    @SerializedName("deployments_url")
    val deploymentsUrl: String?,
    val description: String?,
    val disabled: Boolean?,
    @SerializedName("downloads_url")
    val downloadsUrl: String?,
    @SerializedName("events_url")
    val eventsUrl: String?,
    val fork: Boolean?,
    val forks: Int?,
    @SerializedName("forks_count")
    val forksCount: Int?,
    @SerializedName("forks_url")
    val forksUrl: String?,
    @SerializedName("full_name")
    val fullName: String?,
    @SerializedName("git_commits_url")
    val gitCommitsUrl: String?,
    @SerializedName("git_refs_url")
    val gitRefsUrl: String?,
    @SerializedName("git_tags_url")
    val gitTagsUrl: String?,
    @SerializedName("git_url")
    val gitUrl: String?,
    @SerializedName("has_discussions")
    val hasDiscussions: Boolean?,
    @SerializedName("has_downloads")
    val hasDownloads: Boolean?,
    @SerializedName("has_issues")
    val hasIssues: Boolean?,
    @SerializedName("has_pages")
    val hasPages: Boolean?,
    @SerializedName("has_projects")
    val hasProjects: Boolean?,
    @SerializedName("has_wiki")
    val hasWiki: Boolean?,
    val homepage: String?,
    @SerializedName("hooks_url")
    val hooksUrl: String?,
    @SerializedName("html_url")
    val htmlUrl: String?,
    val id: Int?,
    @SerializedName("is_template")
    val isTemplate: Boolean?,
    @SerializedName("issue_comment_url")
    val issueCommentUrl: String?,
    @SerializedName("issue_events_url")
    val issueEventsUrl: String?,
    @SerializedName("issues_url")
    val issuesUrl: String?,
    @SerializedName("keys_url")
    val keysUrl: String?,
    @SerializedName("labels_url")
    val labelsUrl: String?,
    @SerializedName("languages_url")
    val languagesUrl: String?,
    val license: License?,
    @SerializedName("merges_url")
    val mergesUrl: String?,
    @SerializedName("milestones_url")
    val milestonesUrl: String?,
    @SerializedName("mirror_url")
    val mirrorUrl: String?,
    val name: String?,
    @SerializedName("network_count")
    val networkCount: Int?,
    @SerializedName("node_id")
    val nodeId: String?,
    @SerializedName("notifications_url")
    val notificationsUrl: String?,
    @SerializedName("open_issues")
    val openIssues: Int?,
    @SerializedName("open_issues_count")
    val openIssuesCount: Int?,
    val organization: Organization?,
    val owner: Owner?,
    val parent: Parent?,
    val permissions: Permissions?,
    val `private`: Boolean?,
    @SerializedName("pulls_url")
    val pullsUrl: String?,
    @SerializedName("pushed_at")
    val pushedAt: String?,
    @SerializedName("releases_url")
    val releasesUrl: String?,
    val size: Int?,
    val source: Source?,
    @SerializedName("ssh_url")
    val sshUrl: String?,
    @SerializedName("stargazers_count")
    val stargazersCount: Int?,
    @SerializedName("stargazers_url")
    val stargazersUrl: String?,
    @SerializedName("statuses_url")
    val statusesUrl: String?,
    @SerializedName("subscribers_count")
    val subscribersCount: Int?,
    @SerializedName("subscribers_url")
    val subscribersUrl: String?,
    @SerializedName("subscription_url")
    val subscriptionUrl: String?,
    @SerializedName("svn_url")
    val svnUrl: String?,
    @SerializedName("tags_url")
    val tagsUrl: String?,
    @SerializedName("teams_url")
    val teamsUrl: String?,
    @SerializedName("temp_clone_token")
    val tempCloneToken: String?,
    @SerializedName("template_repository")
    val templateRepository: TemplateRepository?,
    val topics: List<String?>?,
    @SerializedName("trees_url")
    val treesUrl: String?,
    @SerializedName("updated_at")
    val updatedAt: String?,
    val url: String?,
    val visibility: String?,
    val watchers: Int?,
    @SerializedName("watchers_count")
    val watchersCount: Int?
) {
    data class License(
        val key: String?,
        val name: String?,
        @SerializedName("node_id")
        val nodeId: String?,
        @SerializedName("spdx_id")
        val spdxId: String?,
        val url: String?
    )

    data class Organization(
        @SerializedName("avatar_url")
        val avatarUrl: String?,
        @SerializedName("events_url")
        val eventsUrl: String?,
        @SerializedName("followers_url")
        val followersUrl: String?,
        @SerializedName("following_url")
        val followingUrl: String?,
        @SerializedName("gists_url")
        val gistsUrl: String?,
        @SerializedName("gravatar_id")
        val gravatarId: String?,
        @SerializedName("html_url")
        val htmlUrl: String?,
        val id: Int?,
        val login: String?,
        @SerializedName("node_id")
        val nodeId: String?,
        @SerializedName("organizations_url")
        val organizationsUrl: String?,
        @SerializedName("received_events_url")
        val receivedEventsUrl: String?,
        @SerializedName("repos_url")
        val reposUrl: String?,
        @SerializedName("site_admin")
        val siteAdmin: Boolean?,
        @SerializedName("starred_url")
        val starredUrl: String?,
        @SerializedName("subscriptions_url")
        val subscriptionsUrl: String?,
        val type: String?,
        val url: String?
    )

    data class Owner(
        @SerializedName("avatar_url")
        val avatarUrl: String?,
        @SerializedName("events_url")
        val eventsUrl: String?,
        @SerializedName("followers_url")
        val followersUrl: String?,
        @SerializedName("following_url")
        val followingUrl: String?,
        @SerializedName("gists_url")
        val gistsUrl: String?,
        @SerializedName("gravatar_id")
        val gravatarId: String?,
        @SerializedName("html_url")
        val htmlUrl: String?,
        val id: Int?,
        val login: String?,
        @SerializedName("node_id")
        val nodeId: String?,
        @SerializedName("organizations_url")
        val organizationsUrl: String?,
        @SerializedName("received_events_url")
        val receivedEventsUrl: String?,
        @SerializedName("repos_url")
        val reposUrl: String?,
        @SerializedName("site_admin")
        val siteAdmin: Boolean?,
        @SerializedName("starred_url")
        val starredUrl: String?,
        @SerializedName("subscriptions_url")
        val subscriptionsUrl: String?,
        val type: String?,
        val url: String?
    )

    data class Parent(
        @SerializedName("allow_auto_merge")
        val allowAutoMerge: Boolean?,
        @SerializedName("allow_merge_commit")
        val allowMergeCommit: Boolean?,
        @SerializedName("allow_rebase_merge")
        val allowRebaseMerge: Boolean?,
        @SerializedName("allow_squash_merge")
        val allowSquashMerge: Boolean?,
        @SerializedName("archive_url")
        val archiveUrl: String?,
        val archived: Boolean?,
        @SerializedName("assignees_url")
        val assigneesUrl: String?,
        @SerializedName("blobs_url")
        val blobsUrl: String?,
        @SerializedName("branches_url")
        val branchesUrl: String?,
        @SerializedName("clone_url")
        val cloneUrl: String?,
        @SerializedName("collaborators_url")
        val collaboratorsUrl: String?,
        @SerializedName("comments_url")
        val commentsUrl: String?,
        @SerializedName("commits_url")
        val commitsUrl: String?,
        @SerializedName("compare_url")
        val compareUrl: String?,
        @SerializedName("contents_url")
        val contentsUrl: String?,
        @SerializedName("contributors_url")
        val contributorsUrl: String?,
        @SerializedName("created_at")
        val createdAt: String?,
        @SerializedName("default_branch")
        val defaultBranch: String?,
        @SerializedName("delete_branch_on_merge")
        val deleteBranchOnMerge: Boolean?,
        @SerializedName("deployments_url")
        val deploymentsUrl: String?,
        val description: String?,
        val disabled: Boolean?,
        @SerializedName("downloads_url")
        val downloadsUrl: String?,
        @SerializedName("events_url")
        val eventsUrl: String?,
        val fork: Boolean?,
        val forks: Int?,
        @SerializedName("forks_count")
        val forksCount: Int?,
        @SerializedName("forks_url")
        val forksUrl: String?,
        @SerializedName("full_name")
        val fullName: String?,
        @SerializedName("git_commits_url")
        val gitCommitsUrl: String?,
        @SerializedName("git_refs_url")
        val gitRefsUrl: String?,
        @SerializedName("git_tags_url")
        val gitTagsUrl: String?,
        @SerializedName("git_url")
        val gitUrl: String?,
        @SerializedName("has_downloads")
        val hasDownloads: Boolean?,
        @SerializedName("has_issues")
        val hasIssues: Boolean?,
        @SerializedName("has_pages")
        val hasPages: Boolean?,
        @SerializedName("has_projects")
        val hasProjects: Boolean?,
        @SerializedName("has_wiki")
        val hasWiki: Boolean?,
        val homepage: String?,
        @SerializedName("hooks_url")
        val hooksUrl: String?,
        @SerializedName("html_url")
        val htmlUrl: String?,
        val id: Int?,
        @SerializedName("is_template")
        val isTemplate: Boolean?,
        @SerializedName("issue_comment_url")
        val issueCommentUrl: String?,
        @SerializedName("issue_events_url")
        val issueEventsUrl: String?,
        @SerializedName("issues_url")
        val issuesUrl: String?,
        @SerializedName("keys_url")
        val keysUrl: String?,
        @SerializedName("labels_url")
        val labelsUrl: String?,
        val language: Any?,
        @SerializedName("languages_url")
        val languagesUrl: String?,
        val license: License?,
        @SerializedName("merges_url")
        val mergesUrl: String?,
        @SerializedName("milestones_url")
        val milestonesUrl: String?,
        @SerializedName("mirror_url")
        val mirrorUrl: String?,
        val name: String?,
        @SerializedName("network_count")
        val networkCount: Int?,
        @SerializedName("node_id")
        val nodeId: String?,
        @SerializedName("notifications_url")
        val notificationsUrl: String?,
        @SerializedName("open_issues")
        val openIssues: Int?,
        @SerializedName("open_issues_count")
        val openIssuesCount: Int?,
        val owner: Owner?,
        val permissions: Permissions?,
        val `private`: Boolean?,
        @SerializedName("pulls_url")
        val pullsUrl: String?,
        @SerializedName("pushed_at")
        val pushedAt: String?,
        @SerializedName("releases_url")
        val releasesUrl: String?,
        val size: Int?,
        @SerializedName("ssh_url")
        val sshUrl: String?,
        @SerializedName("stargazers_count")
        val stargazersCount: Int?,
        @SerializedName("stargazers_url")
        val stargazersUrl: String?,
        @SerializedName("statuses_url")
        val statusesUrl: String?,
        @SerializedName("subscribers_count")
        val subscribersCount: Int?,
        @SerializedName("subscribers_url")
        val subscribersUrl: String?,
        @SerializedName("subscription_url")
        val subscriptionUrl: String?,
        @SerializedName("svn_url")
        val svnUrl: String?,
        @SerializedName("tags_url")
        val tagsUrl: String?,
        @SerializedName("teams_url")
        val teamsUrl: String?,
        @SerializedName("temp_clone_token")
        val tempCloneToken: String?,
        val topics: List<String?>?,
        @SerializedName("trees_url")
        val treesUrl: String?,
        @SerializedName("updated_at")
        val updatedAt: String?,
        val url: String?,
        val visibility: String?,
        val watchers: Int?,
        @SerializedName("watchers_count")
        val watchersCount: Int?
    ) {
        data class License(
            @SerializedName("html_url")
            val htmlUrl: String?,
            val key: String?,
            val name: String?,
            @SerializedName("node_id")
            val nodeId: String?,
            @SerializedName("spdx_id")
            val spdxId: String?,
            val url: String?
        )

        data class Owner(
            @SerializedName("avatar_url")
            val avatarUrl: String?,
            @SerializedName("events_url")
            val eventsUrl: String?,
            @SerializedName("followers_url")
            val followersUrl: String?,
            @SerializedName("following_url")
            val followingUrl: String?,
            @SerializedName("gists_url")
            val gistsUrl: String?,
            @SerializedName("gravatar_id")
            val gravatarId: String?,
            @SerializedName("html_url")
            val htmlUrl: String?,
            val id: Int?,
            val login: String?,
            @SerializedName("node_id")
            val nodeId: String?,
            @SerializedName("organizations_url")
            val organizationsUrl: String?,
            @SerializedName("received_events_url")
            val receivedEventsUrl: String?,
            @SerializedName("repos_url")
            val reposUrl: String?,
            @SerializedName("site_admin")
            val siteAdmin: Boolean?,
            @SerializedName("starred_url")
            val starredUrl: String?,
            @SerializedName("subscriptions_url")
            val subscriptionsUrl: String?,
            val type: String?,
            val url: String?
        )

        data class Permissions(
            val admin: Boolean?,
            val pull: Boolean?,
            val push: Boolean?
        )
    }

    data class Permissions(
        val admin: Boolean?,
        val pull: Boolean?,
        val push: Boolean?
    )

    data class Source(
        @SerializedName("allow_auto_merge")
        val allowAutoMerge: Boolean?,
        @SerializedName("allow_merge_commit")
        val allowMergeCommit: Boolean?,
        @SerializedName("allow_rebase_merge")
        val allowRebaseMerge: Boolean?,
        @SerializedName("allow_squash_merge")
        val allowSquashMerge: Boolean?,
        @SerializedName("archive_url")
        val archiveUrl: String?,
        val archived: Boolean?,
        @SerializedName("assignees_url")
        val assigneesUrl: String?,
        @SerializedName("blobs_url")
        val blobsUrl: String?,
        @SerializedName("branches_url")
        val branchesUrl: String?,
        @SerializedName("clone_url")
        val cloneUrl: String?,
        @SerializedName("collaborators_url")
        val collaboratorsUrl: String?,
        @SerializedName("comments_url")
        val commentsUrl: String?,
        @SerializedName("commits_url")
        val commitsUrl: String?,
        @SerializedName("compare_url")
        val compareUrl: String?,
        @SerializedName("contents_url")
        val contentsUrl: String?,
        @SerializedName("contributors_url")
        val contributorsUrl: String?,
        @SerializedName("created_at")
        val createdAt: String?,
        @SerializedName("default_branch")
        val defaultBranch: String?,
        @SerializedName("delete_branch_on_merge")
        val deleteBranchOnMerge: Boolean?,
        @SerializedName("deployments_url")
        val deploymentsUrl: String?,
        val description: String?,
        val disabled: Boolean?,
        @SerializedName("downloads_url")
        val downloadsUrl: String?,
        @SerializedName("events_url")
        val eventsUrl: String?,
        val fork: Boolean?,
        val forks: Int?,
        @SerializedName("forks_count")
        val forksCount: Int?,
        @SerializedName("forks_url")
        val forksUrl: String?,
        @SerializedName("full_name")
        val fullName: String?,
        @SerializedName("git_commits_url")
        val gitCommitsUrl: String?,
        @SerializedName("git_refs_url")
        val gitRefsUrl: String?,
        @SerializedName("git_tags_url")
        val gitTagsUrl: String?,
        @SerializedName("git_url")
        val gitUrl: String?,
        @SerializedName("has_downloads")
        val hasDownloads: Boolean?,
        @SerializedName("has_issues")
        val hasIssues: Boolean?,
        @SerializedName("has_pages")
        val hasPages: Boolean?,
        @SerializedName("has_projects")
        val hasProjects: Boolean?,
        @SerializedName("has_wiki")
        val hasWiki: Boolean?,
        val homepage: String?,
        @SerializedName("hooks_url")
        val hooksUrl: String?,
        @SerializedName("html_url")
        val htmlUrl: String?,
        val id: Int?,
        @SerializedName("is_template")
        val isTemplate: Boolean?,
        @SerializedName("issue_comment_url")
        val issueCommentUrl: String?,
        @SerializedName("issue_events_url")
        val issueEventsUrl: String?,
        @SerializedName("issues_url")
        val issuesUrl: String?,
        @SerializedName("keys_url")
        val keysUrl: String?,
        @SerializedName("labels_url")
        val labelsUrl: String?,
        @SerializedName("languages_url")
        val languagesUrl: String?,
        val license: License?,
        @SerializedName("merges_url")
        val mergesUrl: String?,
        @SerializedName("milestones_url")
        val milestonesUrl: String?,
        @SerializedName("mirror_url")
        val mirrorUrl: String?,
        val name: String?,
        @SerializedName("network_count")
        val networkCount: Int?,
        @SerializedName("node_id")
        val nodeId: String?,
        @SerializedName("notifications_url")
        val notificationsUrl: String?,
        @SerializedName("open_issues")
        val openIssues: Int?,
        @SerializedName("open_issues_count")
        val openIssuesCount: Int?,
        val owner: Owner?,
        val permissions: Permissions?,
        val `private`: Boolean?,
        @SerializedName("pulls_url")
        val pullsUrl: String?,
        @SerializedName("pushed_at")
        val pushedAt: String?,
        @SerializedName("releases_url")
        val releasesUrl: String?,
        @SerializedName("security_and_analysis")
        val securityAndAnalysis: SecurityAndAnalysis?,
        val size: Int?,
        @SerializedName("ssh_url")
        val sshUrl: String?,
        @SerializedName("stargazers_count")
        val stargazersCount: Int?,
        @SerializedName("stargazers_url")
        val stargazersUrl: String?,
        @SerializedName("statuses_url")
        val statusesUrl: String?,
        @SerializedName("subscribers_count")
        val subscribersCount: Int?,
        @SerializedName("subscribers_url")
        val subscribersUrl: String?,
        @SerializedName("subscription_url")
        val subscriptionUrl: String?,
        @SerializedName("svn_url")
        val svnUrl: String?,
        @SerializedName("tags_url")
        val tagsUrl: String?,
        @SerializedName("teams_url")
        val teamsUrl: String?,
        @SerializedName("temp_clone_token")
        val tempCloneToken: String?,
        val topics: List<String?>?,
        @SerializedName("trees_url")
        val treesUrl: String?,
        @SerializedName("updated_at")
        val updatedAt: String?,
        val url: String?,
        val visibility: String?,
        val watchers: Int?,
        @SerializedName("watchers_count")
        val watchersCount: Int?
    ) {
        data class License(
            @SerializedName("html_url")
            val htmlUrl: String?,
            val key: String?,
            val name: String?,
            @SerializedName("node_id")
            val nodeId: String?,
            @SerializedName("spdx_id")
            val spdxId: String?,
            val url: String?
        )

        data class Owner(
            @SerializedName("avatar_url")
            val avatarUrl: String?,
            @SerializedName("events_url")
            val eventsUrl: String?,
            @SerializedName("followers_url")
            val followersUrl: String?,
            @SerializedName("following_url")
            val followingUrl: String?,
            @SerializedName("gists_url")
            val gistsUrl: String?,
            @SerializedName("gravatar_id")
            val gravatarId: String?,
            @SerializedName("html_url")
            val htmlUrl: String?,
            val id: Int?,
            val login: String?,
            @SerializedName("node_id")
            val nodeId: String?,
            @SerializedName("organizations_url")
            val organizationsUrl: String?,
            @SerializedName("received_events_url")
            val receivedEventsUrl: String?,
            @SerializedName("repos_url")
            val reposUrl: String?,
            @SerializedName("site_admin")
            val siteAdmin: Boolean?,
            @SerializedName("starred_url")
            val starredUrl: String?,
            @SerializedName("subscriptions_url")
            val subscriptionsUrl: String?,
            val type: String?,
            val url: String?
        )

        data class Permissions(
            val admin: Boolean?,
            val pull: Boolean?,
            val push: Boolean?
        )

        data class SecurityAndAnalysis(
            @SerializedName("advanced_security")
            val advancedSecurity: AdvancedSecurity?,
            @SerializedName("secret_scanning")
            val secretScanning: SecretScanning?,
            @SerializedName("secret_scanning_non_provider_patterns")
            val secretScanningNonProviderPatterns: SecretScanningNonProviderPatterns?,
            @SerializedName("secret_scanning_push_protection")
            val secretScanningPushProtection: SecretScanningPushProtection?
        ) {
            data class AdvancedSecurity(
                val status: String?
            )

            data class SecretScanning(
                val status: String?
            )

            data class SecretScanningNonProviderPatterns(
                val status: String?
            )

            data class SecretScanningPushProtection(
                val status: String?
            )
        }
    }

    data class TemplateRepository(
        @SerializedName("allow_auto_merge")
        val allowAutoMerge: Boolean?,
        @SerializedName("allow_merge_commit")
        val allowMergeCommit: Boolean?,
        @SerializedName("allow_rebase_merge")
        val allowRebaseMerge: Boolean?,
        @SerializedName("allow_squash_merge")
        val allowSquashMerge: Boolean?,
        @SerializedName("archive_url")
        val archiveUrl: String?,
        val archived: Boolean?,
        @SerializedName("assignees_url")
        val assigneesUrl: String?,
        @SerializedName("blobs_url")
        val blobsUrl: String?,
        @SerializedName("branches_url")
        val branchesUrl: String?,
        @SerializedName("clone_url")
        val cloneUrl: String?,
        @SerializedName("collaborators_url")
        val collaboratorsUrl: String?,
        @SerializedName("comments_url")
        val commentsUrl: String?,
        @SerializedName("commits_url")
        val commitsUrl: String?,
        @SerializedName("compare_url")
        val compareUrl: String?,
        @SerializedName("contents_url")
        val contentsUrl: String?,
        @SerializedName("contributors_url")
        val contributorsUrl: String?,
        @SerializedName("created_at")
        val createdAt: String?,
        @SerializedName("default_branch")
        val defaultBranch: String?,
        @SerializedName("delete_branch_on_merge")
        val deleteBranchOnMerge: Boolean?,
        @SerializedName("deployments_url")
        val deploymentsUrl: String?,
        val description: String?,
        val disabled: Boolean?,
        @SerializedName("downloads_url")
        val downloadsUrl: String?,
        @SerializedName("events_url")
        val eventsUrl: String?,
        val fork: Boolean?,
        val forks: Int?,
        @SerializedName("forks_count")
        val forksCount: Int?,
        @SerializedName("forks_url")
        val forksUrl: String?,
        @SerializedName("full_name")
        val fullName: String?,
        @SerializedName("git_commits_url")
        val gitCommitsUrl: String?,
        @SerializedName("git_refs_url")
        val gitRefsUrl: String?,
        @SerializedName("git_tags_url")
        val gitTagsUrl: String?,
        @SerializedName("git_url")
        val gitUrl: String?,
        @SerializedName("has_downloads")
        val hasDownloads: Boolean?,
        @SerializedName("has_issues")
        val hasIssues: Boolean?,
        @SerializedName("has_pages")
        val hasPages: Boolean?,
        @SerializedName("has_projects")
        val hasProjects: Boolean?,
        @SerializedName("has_wiki")
        val hasWiki: Boolean?,
        val homepage: String?,
        @SerializedName("hooks_url")
        val hooksUrl: String?,
        @SerializedName("html_url")
        val htmlUrl: String?,
        val id: Int?,
        @SerializedName("is_template")
        val isTemplate: Boolean?,
        @SerializedName("issue_comment_url")
        val issueCommentUrl: String?,
        @SerializedName("issue_events_url")
        val issueEventsUrl: String?,
        @SerializedName("issues_url")
        val issuesUrl: String?,
        @SerializedName("keys_url")
        val keysUrl: String?,
        @SerializedName("labels_url")
        val labelsUrl: String?,
        val language: Any?,
        @SerializedName("languages_url")
        val languagesUrl: String?,
        val license: License?,
        @SerializedName("merges_url")
        val mergesUrl: String?,
        @SerializedName("milestones_url")
        val milestonesUrl: String?,
        @SerializedName("mirror_url")
        val mirrorUrl: String?,
        val name: String?,
        @SerializedName("network_count")
        val networkCount: Int?,
        @SerializedName("node_id")
        val nodeId: String?,
        @SerializedName("notifications_url")
        val notificationsUrl: String?,
        @SerializedName("open_issues")
        val openIssues: Int?,
        @SerializedName("open_issues_count")
        val openIssuesCount: Int?,
        val owner: Owner?,
        val permissions: Permissions?,
        val `private`: Boolean?,
        @SerializedName("pulls_url")
        val pullsUrl: String?,
        @SerializedName("pushed_at")
        val pushedAt: String?,
        @SerializedName("releases_url")
        val releasesUrl: String?,
        val size: Int?,
        @SerializedName("ssh_url")
        val sshUrl: String?,
        @SerializedName("stargazers_count")
        val stargazersCount: Int?,
        @SerializedName("stargazers_url")
        val stargazersUrl: String?,
        @SerializedName("statuses_url")
        val statusesUrl: String?,
        @SerializedName("subscribers_count")
        val subscribersCount: Int?,
        @SerializedName("subscribers_url")
        val subscribersUrl: String?,
        @SerializedName("subscription_url")
        val subscriptionUrl: String?,
        @SerializedName("svn_url")
        val svnUrl: String?,
        @SerializedName("tags_url")
        val tagsUrl: String?,
        @SerializedName("teams_url")
        val teamsUrl: String?,
        @SerializedName("temp_clone_token")
        val tempCloneToken: String?,
        val topics: List<String?>?,
        @SerializedName("trees_url")
        val treesUrl: String?,
        @SerializedName("updated_at")
        val updatedAt: String?,
        val url: String?,
        val visibility: String?,
        val watchers: Int?,
        @SerializedName("watchers_count")
        val watchersCount: Int?
    ) {
        data class License(
            @SerializedName("html_url")
            val htmlUrl: String?,
            val key: String?,
            val name: String?,
            @SerializedName("node_id")
            val nodeId: String?,
            @SerializedName("spdx_id")
            val spdxId: String?,
            val url: String?
        )

        data class Owner(
            @SerializedName("avatar_url")
            val avatarUrl: String?,
            @SerializedName("events_url")
            val eventsUrl: String?,
            @SerializedName("followers_url")
            val followersUrl: String?,
            @SerializedName("following_url")
            val followingUrl: String?,
            @SerializedName("gists_url")
            val gistsUrl: String?,
            @SerializedName("gravatar_id")
            val gravatarId: String?,
            @SerializedName("html_url")
            val htmlUrl: String?,
            val id: Int?,
            val login: String?,
            @SerializedName("node_id")
            val nodeId: String?,
            @SerializedName("organizations_url")
            val organizationsUrl: String?,
            @SerializedName("received_events_url")
            val receivedEventsUrl: String?,
            @SerializedName("repos_url")
            val reposUrl: String?,
            @SerializedName("site_admin")
            val siteAdmin: Boolean?,
            @SerializedName("starred_url")
            val starredUrl: String?,
            @SerializedName("subscriptions_url")
            val subscriptionsUrl: String?,
            val type: String?,
            val url: String?
        )

        data class Permissions(
            val admin: Boolean?,
            val pull: Boolean?,
            val push: Boolean?
        )
    }
}