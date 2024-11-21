package cotato.backend.domains.post;

import static cotato.backend.common.exception.ErrorCode.*;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cotato.backend.common.excel.ExcelUtils;
import cotato.backend.common.exception.ApiException;
import cotato.backend.domains.post.dto.PostFindResponse;
import cotato.backend.domains.post.dto.PostListFindResponse;
import cotato.backend.domains.post.dto.SinglePostFindResponse;
import cotato.backend.domains.post.dto.request.SinglePostCreateRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Transactional(readOnly = true)
public class PostService {

	private final PostRepository postRepository;

	// 로컬 파일 경로로부터 엑셀 파일을 읽어 Post 엔터티로 변환하고 저장
	@Transactional
	public void saveEstatesByExcel(String filePath) {
		try {
			// 엑셀 파일을 읽어 데이터 프레임 형태로 변환
			List<Post> posts = ExcelUtils.parseExcelFile(filePath).stream()
				.map(row -> {
					String title = row.get("title");
					String content = row.get("content");
					String name = row.get("name");

					return Post.builder()
						.title(title)
						.content(content)
						.name(name)
						.build();
				})
				.toList();
			postRepository.saveAll(posts);
		} catch (Exception e) {
			log.error("Failed to save estates by excel", e);
			throw ApiException.from(INTERNAL_SERVER_ERROR);
		}
	}

	@Transactional
	public SinglePostCreateResponse createSinglePost(SinglePostCreateRequest request) {
		Post post = Post.builder()
			.title(request.title())
			.content(request.content())
			.name(request.name())
			.views(0L)
			.build();

		Post savedPost = postRepository.save(post);
		return SinglePostCreateResponse.from(post);
	}

	@Transactional
	public SinglePostFindResponse findSinglePostById(Long postId) {
		Post post = postRepository.findById(postId)
			.orElseThrow(() -> ApiException.from(POST_NOT_FOUND));

		post.increaseViews();

		return SinglePostFindResponse.from(
			post.getTitle(),
			post.getContent(),
			post.getName(),
			post.getViews()
		);
	}

	public PostListFindResponse findPostListSortByViews(Pageable pageable) {
		List<PostFindResponse> postFindResponses = postRepository.findAllByOrderByViewsDesc(pageable)
			.stream().map(PostFindResponse::from)
			.toList();

		return PostListFindResponse.from(postFindResponses);
	}

	@Transactional
	public void deletePostById(Long postId) {
		Post post = postRepository.findById(postId)
			.orElseThrow(() -> ApiException.from(POST_NOT_FOUND));
		postRepository.deleteById(postId);
	}
}
