package com.example.gitgo.components.network.models


import com.google.gson.annotations.SerializedName

class GitHubRepoIssuesModel : ArrayList<GitHubRepoIssuesModel.GitHubRepoIssuesModelItem>(){
    data class GitHubRepoIssuesModelItem(
        @SerializedName("active_lock_reason")
        val activeLockReason: String?,
        val assignee: Assignee?,
        val assignees: List<Assignee?>?,
        @SerializedName("author_association")
        val authorAssociation: String?,
        val body: String?,
        @SerializedName("closed_at")
        val closedAt: Any?,
        @SerializedName("closed_by")
        val closedBy: ClosedBy?,
        val comments: Int?,
        @SerializedName("comments_url")
        val commentsUrl: String?,
        @SerializedName("created_at")
        val createdAt: String?,
        @SerializedName("events_url")
        val eventsUrl: String?,
        @SerializedName("html_url")
        val htmlUrl: String?,
        val id: Long?,
        val labels: List<Label?>?,
        @SerializedName("labels_url")
        val labelsUrl: String?,
        val locked: Boolean?,
        val milestone: Milestone?,
        @SerializedName("node_id")
        val nodeId: String?,
        val number: Int?,
        @SerializedName("pull_request")
        val pullRequest: PullRequest?,
        @SerializedName("repository_url")
        val repositoryUrl: String?,
        val state: String?,
        @SerializedName("state_reason")
        val stateReason: String?,
        val title: String?,
        @SerializedName("updated_at")
        val updatedAt: String?,
        val url: String?,
        val user: User?
    ) {
        data class Assignee(
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
            val id: Long?,
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
    
        data class ClosedBy(
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
            val id: Long?,
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
    
        data class Label(
            val color: String?,
            val default: Boolean?,
            val description: String?,
            val id: Long?,
            val name: String?,
            @SerializedName("node_id")
            val nodeId: String?,
            val url: String?
        )
    
        data class Milestone(
            @SerializedName("closed_at")
            val closedAt: String?,
            @SerializedName("closed_issues")
            val closedIssues: Int?,
            @SerializedName("created_at")
            val createdAt: String?,
            val creator: Creator?,
            val description: String?,
            @SerializedName("due_on")
            val dueOn: String?,
            @SerializedName("html_url")
            val htmlUrl: String?,
            val id: Long?,
            @SerializedName("labels_url")
            val labelsUrl: String?,
            @SerializedName("node_id")
            val nodeId: String?,
            val number: Int?,
            @SerializedName("open_issues")
            val openIssues: Int?,
            val state: String?,
            val title: String?,
            @SerializedName("updated_at")
            val updatedAt: String?,
            val url: String?
        ) {
            data class Creator(
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
                val id: Long?,
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
        }
    
        data class PullRequest(
            @SerializedName("diff_url")
            val diffUrl: String?,
            @SerializedName("html_url")
            val htmlUrl: String?,
            @SerializedName("patch_url")
            val patchUrl: String?,
            val url: String?
        )
    
        data class User(
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
            val id: Long?,
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
    }
}