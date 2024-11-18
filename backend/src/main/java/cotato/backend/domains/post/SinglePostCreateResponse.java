package cotato.backend.domains.post;

public record SinglePostCreateResponse(
	Long postId
) {
	public static SinglePostCreateResponse from(Post post) {
		return new SinglePostCreateResponse(post.getId());
	}
}