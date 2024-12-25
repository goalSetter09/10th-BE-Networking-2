package cotato.backend.domains.post;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cotato.backend.common.excel.ExcelUtils;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostBatchProcessor {

	private final PostAppender postAppender;

	@Transactional
	public void savePostsByExcelWithBatch(String filePath) throws Exception {
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
		postAppender.appendList(posts);
	}
}