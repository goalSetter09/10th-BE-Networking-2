package cotato.backend.domains.post.dto;

public record SinglePostFindResponse(
	String title,
	String content,
	String author,
	Long views
) {

	public static SinglePostFindResponse from(String title, String content, String author, Long views) {
		return new SinglePostFindResponse(title, content, author, views);
	}
}