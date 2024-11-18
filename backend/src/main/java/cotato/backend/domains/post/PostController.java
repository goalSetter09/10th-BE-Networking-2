package cotato.backend.domains.post;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cotato.backend.common.dto.DataResponse;
import cotato.backend.domains.post.dto.PostListFindResponse;
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

	/*
	게시글 목록 조회 기능 (조회순, 페이징 포함)
		- 게시글 목록을 **조회 수(views)** 순으로 정렬하여 조회하는 API를 구현하세요.
		- `Pageable`을 사용해 페이지네이션을 구현하고, 페이지별로 10개의 게시글을 반환합니다.
		- 각 게시글의 ID, 제목, 작성자 이름을 포함하여 반환합니다.
		- 추가 기능: 페이지네이션에 필요한 현재 페이지와 전체 페이지 수도 함께 반환합니다.
	 */
	@GetMapping()
	public ResponseEntity<DataResponse<PostListFindResponse>> findPostListSortByViews(
		@PageableDefault(page = 0, size = 10) Pageable pageable) {

		return ResponseEntity.ok(DataResponse.from(postService.findPostListSortByViews(pageable)));
	}

	@PostMapping("/excel")
	public ResponseEntity<DataResponse<Void>> savePostsByExcel(@RequestBody SavePostsByExcelRequest request) {
		postService.saveEstatesByExcel(request.getPath());

		return ResponseEntity.ok(DataResponse.ok());
	}
}
