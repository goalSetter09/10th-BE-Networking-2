package cotato.backend.domains.post.dto;

import cotato.backend.domains.post.Post;

public record PostFindResponse(
	Long postId,
	String title,
	String author
) {
	public static PostFindResponse from(Post post) {
		return new PostFindResponse(
			post.getId(),
			post.getTitle(),
			post.getName()
		);
	}
}