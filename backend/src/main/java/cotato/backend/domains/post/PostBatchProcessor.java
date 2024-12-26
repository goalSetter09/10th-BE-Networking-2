package cotato.backend.domains.post;

import static cotato.backend.common.exception.ErrorCode.*;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cotato.backend.common.excel.ExcelUtils;
import cotato.backend.common.exception.ApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostBatchProcessor {

	private final PostJdbcRepository postJdbcRepository;

	@Transactional
	public void savePostsByExcelWithBatch(String filePath) {
		try {
			List<Post> posts = ExcelUtils.parseExcelFile(filePath).stream()
				.map(row -> {
					String title = row.get("title");
					String content = row.get("content");
					String name = row.get("name");

					return Post.builder()
						.title(title)
						.content(content)
						.name(name)
						.views(0L)
						.build();
				})
				.toList();
			postJdbcRepository.saveAll(posts);
		} catch (Exception e) {
			log.error("Failed to save estates by excel", e);
			throw ApiException.from(INTERNAL_SERVER_ERROR);
		}
	}
}