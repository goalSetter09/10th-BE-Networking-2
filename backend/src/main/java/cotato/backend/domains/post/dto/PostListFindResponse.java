package cotato.backend.domains.post.dto;

import java.util.List;

public record PostListFindResponse(
	List<PostFindResponse> postFindResponseList
) {
	public static PostListFindResponse from(List<PostFindResponse> postFindResponseList) {
		return new PostListFindResponse(postFindResponseList);
	}
}