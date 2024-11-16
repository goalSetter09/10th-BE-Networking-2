package cotato.backend.domains.post;

public record SinglePostCreateResponse(
	Long postId
) {
	public static SinglePostCreateResponse from(Long postId) {
		return new SinglePostCreateResponse(postId);
	}
}