package cotato.backend.domains.post.dto.request;

public record SinglePostCreateRequest(
	String title,
	String content,
	String name
) {
}