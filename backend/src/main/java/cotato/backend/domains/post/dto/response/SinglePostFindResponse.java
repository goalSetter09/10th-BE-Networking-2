package cotato.backend.domains.post.dto.response;

import cotato.backend.domains.post.Post;

public record SinglePostFindResponse(
	String title,
	String content,
	String author,
	Long views
) {

	public static SinglePostFindResponse from(Post post) {
		return new SinglePostFindResponse(
			post.getTitle(),
			post.getContent(),
			post.getName(),
			post.getViews()
		);
	}
}