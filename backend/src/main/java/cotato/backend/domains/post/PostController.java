package cotato.backend.domains.post;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "게시글 API", description = "게시글 관련 API")
@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

	private final PostService postService;

	// 게시글 단일 조회 기능
	@GetMapping("/{postId}")
	@Operation(summary = "게시글 단건 조회", description = "postId를 바탕으로 게시글 하나에 대한 정보를 조회합니다")
	public ResponseEntity<DataResponse<SinglePostFindResponse>> findSinglePostById(@PathVariable Long postId) {

		return ResponseEntity.ok(DataResponse.from(postService.findSinglePostById(postId)));
	}

	// 게시글 단일 생성 기능
	@PostMapping("/single")
	@Operation(summary = "게시글 단건 생성", description = "SinglePostCreateRequest를 통해 게시글을 하나 생성합니다.")
	public ResponseEntity<DataResponse<SinglePostCreateResponse>> createSinglePost(
		@RequestBody SinglePostCreateRequest request) {
		SinglePostCreateResponse response = postService.createSinglePost(request);

		return ResponseEntity.ok(DataResponse.from(response));
	}

	@GetMapping()
	@Operation(
		summary = "게시글 리스트 조회(페이징, 조회수 순 정렬)",
		description = "전체 게시글을 조회수 순으로 정렬한 후 페이징하여 조회합니다.(기본 페이지: 0, 페이지 별 기본 게시글 수: 10)"
	)
	public ResponseEntity<DataResponse<PostListFindResponse>> findPostListSortByViews(
		@PageableDefault(page = 0, size = 10) Pageable pageable) {

		return ResponseEntity.ok(DataResponse.from(postService.findPostListSortByViews(pageable)));
	}

	@PostMapping("/excel")
	@Operation(summary = "게시글 다중 생성(엑셀)", description = "SavePostsByExcelRequest(엑셀 파일의 경로)를 통해 다중 게시글을 한 번에 생성합니다.")
	public ResponseEntity<DataResponse<Void>> savePostsByExcel(@RequestBody SavePostsByExcelRequest request) {
		postService.saveEstatesByExcel(request.getPath());

		return ResponseEntity.ok(DataResponse.ok());
	}

	@DeleteMapping("/{postId}")
	@Operation(summary = "게시글 단건 삭제", description = "postId를 통해 게시글을 하나 삭제합니다.")
	public ResponseEntity<DataResponse<String>> deletePostById(@PathVariable Long postId) {
		postService.deletePostById(postId);
		return ResponseEntity.ok(DataResponse.from("게시글이 정상적으로 삭제되었습니다."));
	}
}
