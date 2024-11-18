package cotato.backend.domains.post;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cotato.backend.common.dto.DataResponse;
import cotato.backend.domains.post.dto.SinglePostFindResponse;
import cotato.backend.domains.post.dto.request.SavePostsByExcelRequest;
import cotato.backend.domains.post.dto.request.SinglePostCreateRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

	private final PostService postService;

	// 게시글 단일 생성 기능
	@PostMapping("/single")
	public ResponseEntity<DataResponse<SinglePostCreateResponse>> createSinglePost(
		@RequestBody SinglePostCreateRequest request) {
		SinglePostCreateResponse response = postService.createSinglePost(request);
		return ResponseEntity.ok(DataResponse.from(response));
	}

	// 게시글 단일 조회 기능
	@GetMapping("/{postId}")
	public ResponseEntity<DataResponse<SinglePostFindResponse>> findSinglePostById(@PathVariable Long postId) {

		return ResponseEntity.ok(DataResponse.from(postService.findSinglePostById(postId)));
	}

	@PostMapping("/excel")
	public ResponseEntity<DataResponse<Void>> savePostsByExcel(@RequestBody SavePostsByExcelRequest request) {
		postService.saveEstatesByExcel(request.getPath());

		return ResponseEntity.ok(DataResponse.ok());
	}
}
